package com.example.daggerandroid.ui.auth

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
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
            user_id_input?.let {
                viewModel.authenticateWithId(user_id_input.text.toString())
            }
        }

        subscribeObserver()
    }

    private fun subscribeObserver() {
        viewModel.observerUser().observe(this, Observer<AuthResource<User>> {
            it?.let {
                when(it.status) {
                    AuthResource.AuthStatus.AUTHENTICATED -> {
                        showProgress(false)
                        Log.d(TAG, it.data!!.email)
                    }
                    AuthResource.AuthStatus.ERROR -> {
                        showProgress(false)
                        Toast.makeText(this, "Enter ID between 1 and 10", Toast.LENGTH_SHORT).show()
                    }
                    AuthResource.AuthStatus.LOADING -> {
                        showProgress(true)
                    }
                    AuthResource.AuthStatus.NOT_AUTHENTICATED -> {
                        showProgress(false)
                    }
                }
            }
        })
    }

    private fun showProgress(isVisible: Boolean) {
        if (isVisible)
            progress_bar.visibility = View.VISIBLE
        else
            progress_bar.visibility = View.GONE
    }

    private fun setLogo() {
        requestManager.load(logo)
            .into(login_logo)
    }

    companion object {
        const val TAG = "AuthActivity"
    }
}
