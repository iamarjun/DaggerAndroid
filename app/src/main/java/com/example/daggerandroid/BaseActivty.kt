package com.example.daggerandroid

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.daggerandroid.ui.auth.AuthActivity
import com.example.daggerandroid.ui.auth.AuthResource
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity : DaggerAppCompatActivity() {

    @Inject
    internal lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun subscribeObserver() {
        sessionManager.getAuthUser().observe(this, Observer {

            it?.let {
                when (it.status) {

                    AuthResource.AuthStatus.LOADING -> {

                    }
                    AuthResource.AuthStatus.AUTHENTICATED -> {
                        Toast.makeText(this, "Authenticated", Toast.LENGTH_LONG).show()
                    }
                    AuthResource.AuthStatus.ERROR -> {
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    AuthResource.AuthStatus.NOT_AUTHENTICATED -> {
                        navLoginScreen()
                    }

                }

            }

        })
    }

    private fun navLoginScreen() {
        startActivity(Intent(this, AuthActivity::class.java))
        finish()

    }

}