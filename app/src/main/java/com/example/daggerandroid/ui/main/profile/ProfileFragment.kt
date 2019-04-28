package com.example.daggerandroid.ui.main.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.daggerandroid.R
import com.example.daggerandroid.model.User
import com.example.daggerandroid.ui.auth.AuthResource
import com.example.daggerandroid.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_profile.view.*
import javax.inject.Inject

class ProfileFragment : DaggerFragment() {

    private lateinit var viewModel: ProfileViewModel

    @Inject
    internal lateinit var providerFactory: ViewModelProviderFactory


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this, providerFactory).get(ProfileViewModel::class.java)

        subscribeObserver()
    }


    private fun subscribeObserver() {
        viewModel.getAuthenticatedUser().removeObservers(viewLifecycleOwner)
        viewModel.getAuthenticatedUser().observe(viewLifecycleOwner, Observer {

            it?.let {
                when (it.status) {

                    AuthResource.AuthStatus.AUTHENTICATED -> {
                        setUserDetails(it.data)
                    }
                    AuthResource.AuthStatus.ERROR -> {
                        setErrorDetails(it.message)
                    }

                    else -> {
                        setErrorDetails(it.message)
                    }

                }

            }
        })
    }

    private fun setErrorDetails(message: String?) {
        view!!.email.text = message
        view!!.username.text = "error"
        view!!.website.text = "erro"

    }

    private fun setUserDetails(data: User?) {
        view!!.email.text = data!!.email
        view!!.username.text = data.username
        view!!.website.text = data.website
    }
}