package com.example.daggerandroid.ui.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.daggerandroid.network.auth.AuthApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AuthViewModel @Inject constructor(private val authApi: AuthApi): ViewModel() {

    init {
        Log.d(TAG, "AuthViewModel: ViewModel is working...")

        if (authApi == null)
            Log.d(TAG, "AuthViewModel: AuthiApi is NULL...")
        else
            Log.d(TAG, "AuthViewModel: AuthiApi is NOT NULL...")

        authApi.getUser("1")
            .toObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                Log.d(TAG, "onNext: ${it.email}")
            }
            .doOnError {
                Log.d(TAG, "onError: ${it.message}")
            }
            .subscribe({}, {})
    }

    companion object {
        private const val TAG = "AuthViewModel"
    }

}