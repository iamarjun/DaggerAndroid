package com.example.daggerandroid.ui.auth

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.RequestManager
import com.example.daggerandroid.R
import com.example.daggerandroid.model.User
import com.example.daggerandroid.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_auth.*
import javax.inject.Inject

class AuthActivity : DaggerAppCompatActivity() {

    @Inject
    internal lateinit var logo: Drawable

    @Inject
    internal lateinit var requestManager: RequestManager

    @Inject
    internal lateinit var providerFactory: ViewModelProviderFactory

    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        viewModel = ViewModelProviders.of(this, providerFactory).get(AuthViewModel::class.java)

        setLogo()

        login_button.setOnClickListener {
            if (TextUtils.isEmpty(user_id_input.text))
                return@setOnClickListener


            viewModel.authenticateWithId(user_id_input.text.toString())
        }

        subscribeObserver()
    }

    private fun subscribeObserver() {
        viewModel.observerUser().observe(this, Observer<User> { t ->
            t?.let {
                Log.d(TAG, "onChanged: ${it.email}")
            }
        })
    }

    private fun setLogo() {
        requestManager.load(logo)
            .into(login_logo)
    }

    companion object {
        private const val TAG = "AuthActivity"
    }
}
