package com.example.daggerandroid.ui.auth

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.RequestManager
import com.example.daggerandroid.MainActivity
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

    private fun showProgress(visibility: Boolean) {
        if (visibility)
            progress_bar.visibility = View.VISIBLE
        else
            progress_bar.visibility = View.GONE
    }

    private fun subscribeObserver() {
        viewModel.observerAuthState().observe(this, Observer<AuthResource<User>> { t ->
            t?.let {
                when (it.status) {

                    AuthResource.AuthStatus.LOADING -> {
                        showProgress(true)
                    }
                    AuthResource.AuthStatus.AUTHENTICATED -> {
                        showProgress(false)
                        onLoginSuccess()
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()

                    }
                    AuthResource.AuthStatus.ERROR -> {
                        showProgress(false)
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()

                    }
                    AuthResource.AuthStatus.NOT_AUTHENTICATED -> {
                        showProgress(false)

                    }

                }

            }
        })
    }

    private fun onLoginSuccess() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun setLogo() {
        requestManager.load(logo)
            .into(login_logo)
    }

    companion object {
        private const val TAG = "AuthActivity"
    }
}
