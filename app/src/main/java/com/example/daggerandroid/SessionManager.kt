package com.example.daggerandroid

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.daggerandroid.model.User
import com.example.daggerandroid.ui.auth.AuthResource
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SessionManager @Inject constructor() {

    private val cachedUser = MediatorLiveData<AuthResource<User>>()

    fun authenticateWithId(source: LiveData<AuthResource<User>>) {

        cachedUser.let {
            cachedUser.value = AuthResource.loading(null)
            cachedUser.addSource(source) {
                cachedUser.value = it
                cachedUser.removeSource(source)
            }
        }
    }

    fun logout() {
        cachedUser.value = AuthResource.logout()
    }

    fun getAuthUser(): LiveData<AuthResource<User>> = cachedUser
}