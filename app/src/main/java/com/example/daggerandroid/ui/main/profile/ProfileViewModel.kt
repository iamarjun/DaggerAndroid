package com.example.daggerandroid.ui.main.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.daggerandroid.SessionManager
import com.example.daggerandroid.model.User
import com.example.daggerandroid.ui.auth.AuthResource
import javax.inject.Inject

class ProfileViewModel @Inject constructor(private val sessionManager: SessionManager): ViewModel() {

    fun getAuthenticatedUser(): LiveData<AuthResource<User>> = sessionManager.getAuthUser()
}