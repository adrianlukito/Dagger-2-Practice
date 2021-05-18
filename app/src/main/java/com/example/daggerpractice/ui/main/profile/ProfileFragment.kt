package com.example.daggerpractice.ui.main.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.daggerpractice.R
import com.example.daggerpractice.models.User
import com.example.daggerpractice.ui.auth.AuthResource
import com.example.daggerpractice.viewmodels.ViewModelProvidersFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_profile.*
import javax.inject.Inject

class ProfileFragment: DaggerFragment() {

    private lateinit var viewModel: ProfileViewModel

    @Inject
    lateinit var viewModelProvidersFactory: ViewModelProvidersFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Toast.makeText(requireContext(), "dasdasdas", Toast.LENGTH_SHORT).show()
        return layoutInflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelProvidersFactory).get(ProfileViewModel::class.java)

        subscribeObserver()
    }

    private fun subscribeObserver() {
        viewModel.getAuthenticatedUser().removeObservers(viewLifecycleOwner)
        viewModel.getAuthenticatedUser().observe(viewLifecycleOwner, Observer {
            when(it.status) {
                AuthResource.Status.AUTHENTICATED -> {
                    it.data?.let { user ->  setUserDetails(user) }
                }
                AuthResource.Status.ERROR -> {
                    setErrorDetails(it.error)
                }
            }
        })
    }

    private fun setErrorDetails(error: Throwable?) {
        username.text = "Error"
        email.text = error?.message
        website.text = "Error"
    }

    private fun setUserDetails(user: User) {
        username.text = user.username
        email.text = user.email
        website.text = user.website
    }
}