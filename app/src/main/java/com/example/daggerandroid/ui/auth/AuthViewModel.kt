package com.example.daggerandroid.ui.auth

import android.util.Log
import androidx.lifecycle.*
import com.example.daggerandroid.model.User
import com.example.daggerandroid.network.auth.AuthApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AuthViewModel @Inject constructor(private val authApi: AuthApi): ViewModel() {

    private val authUser = MediatorLiveData<User>()

    init {
        Log.d(TAG, "AuthViewModel: ViewModel is working...")

        if (authApi == null)
            Log.d(TAG, "AuthViewModel: AuthiApi is NULL...")
        else
            Log.d(TAG, "AuthViewModel: AuthiApi is NOT NULL...")


    }

    fun authenticateWithId(id: String) {
        val source = LiveDataReactiveStreams.fromPublisher<User>(
            authApi.getUser(id)
                .subscribeOn(Schedulers.io())
        )

        authUser.addSource(source, object : Observer<User> {
            override fun onChanged(t: User?) {
                authUser.value = t
                authUser.removeSource(source)
            }

        })
    }

    fun observerUser(): LiveData<User> {
        return authUser
    }
    companion object {
        private const val TAG = "AuthViewModel"
    }

}