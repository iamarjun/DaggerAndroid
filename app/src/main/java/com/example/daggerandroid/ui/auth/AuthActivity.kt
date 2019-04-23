package com.example.daggerandroid.ui.auth

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.RequestManager
import com.example.daggerandroid.R
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
    }

    private fun setLogo() {
        requestManager.load(logo)
            .into(login_logo)
    }
}
