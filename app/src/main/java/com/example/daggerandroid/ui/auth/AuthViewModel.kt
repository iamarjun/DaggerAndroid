package com.example.daggerandroid.ui.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.example.daggerandroid.SessionManager
import com.example.daggerandroid.model.User
import com.example.daggerandroid.network.auth.AuthApi
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AuthViewModel @Inject constructor(private val authApi: AuthApi, private val sessionManager: SessionManager) :
    ViewModel() {

    init {
        Log.d(TAG, "AuthViewModel: ViewModel is working...")
    }

    fun authenticateWithId(id: String) {

        sessionManager.authenticateWithId(queryUserId(id))
    }

    private fun queryUserId(id: String): LiveData<AuthResource<User>> {

        return LiveDataReactiveStreams.fromPublisher<AuthResource<User>>(
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
    }

    fun observerAuthState(): LiveData<AuthResource<User>> {
        return sessionManager.getAuthUser()
    }

    companion object {
        private const val TAG = "AuthViewModel"
    }

}

