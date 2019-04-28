package com.example.daggerandroid.ui.main.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.daggerandroid.R
import com.example.daggerandroid.model.Post
import com.example.daggerandroid.util.VerticalSpacingItemDecoration
import com.example.daggerandroid.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_posts.*
import javax.inject.Inject

class PostFragment : DaggerFragment() {

    private lateinit var viewModel: PostViewModel

    @Inject
    internal lateinit var providerFactory: ViewModelProviderFactory

    @Inject
    internal lateinit var adapter: PostAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.fragment_posts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this, providerFactory).get(PostViewModel::class.java)

        initRecyclerView()
        subscribeObserver()

    }

    private fun initRecyclerView() {
        recycler_view.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        val itemDecorator = VerticalSpacingItemDecoration(15)
        recycler_view.addItemDecoration(itemDecorator)
        recycler_view.adapter = adapter
    }

    private fun subscribeObserver() {
        viewModel.observerPost().removeObservers(viewLifecycleOwner)
        viewModel.observerPost().observe(viewLifecycleOwner,
            Observer<PostResource<List<Post>>> { t ->
                t.let {
                    when (it.status) {

                        PostResource.Status.LOADING -> {
                            Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                        }

                        PostResource.Status.SUCCESS -> {
                            adapter.setPosts(it.data!!)
                        }

                        PostResource.Status.ERROR -> {
                            Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
    }

    companion object {
        const val TAG = "PostFragment"
    }

}