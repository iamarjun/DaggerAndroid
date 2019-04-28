package com.example.daggerandroid.ui.main.post

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.daggerandroid.SessionManager
import com.example.daggerandroid.model.Post
import com.example.daggerandroid.network.main.MainApi
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PostViewModel @Inject constructor(private val sessionManager: SessionManager, private val mainApi: MainApi) :
    ViewModel() {

    private val posts = MediatorLiveData<PostResource<List<Post>>>()


    init {
        Log.d(TAG, "PostViewModel created...")
    }

    fun observerPost(): LiveData<PostResource<List<Post>>> {

        posts.value = PostResource.loading(null)

        val source = LiveDataReactiveStreams.fromPublisher<PostResource<List<Post>>>(
            mainApi.getPosts(sessionManager.getAuthUser().value!!.data!!.id!!.toString())
                .onErrorReturn {
                    val post = Post()
                    post.id = -1

                    val posts = ArrayList<Post>()
                    posts.add(post)

                    return@onErrorReturn posts
                }
                .map(Function<List<Post>, PostResource<List<Post>>> {
                    if (it.isNotEmpty())
                        if (it.any { post -> post.id == -1 })
                            return@Function PostResource.error("SomeThing went wrong", null)

                    return@Function PostResource.success(it)
                })
                .subscribeOn(Schedulers.io())
        )

        posts.addSource(source) {
            posts.value = it
            posts.removeSource(source)
        }
        return posts
    }


    companion object {
        private const val TAG = "PostFragment"
    }

}