package com.example.daggerandroid.ui.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.daggerandroid.model.User
import com.example.daggerandroid.network.auth.AuthApi
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AuthViewModel @Inject constructor(private val authApi: AuthApi) : ViewModel() {

    private val authUser = MediatorLiveData<AuthResource<User>>()

    init {
        Log.d(TAG, "AuthViewModel: ViewModel is working...")
    }

    fun authenticateWithId(id: String) {

        authUser.value = AuthResource.loading(null)

        val source = LiveDataReactiveStreams.fromPublisher<AuthResource<User>>(
            authApi.getUser(id)
                .onErrorReturn {
                    val user = User()
                    user.id = -1
                    return@onErrorReturn user
                }
                .map(Function<User, AuthResource<User>> {
                    if (it.id == -1)
                        return@Function AuthResource.error("Could not authenticate", null)

                    return@Function AuthResource.authenticated(it)
                })
                .subscribeOn(Schedulers.io())
        )

        authUser.addSource(source) {
            authUser.value = it
            authUser.removeSource(source)
        }
    }

    fun observerUser(): LiveData<AuthResource<User>> {
        return authUser
    }

    companion object {
        private const val TAG = "AuthViewModel"
    }

}