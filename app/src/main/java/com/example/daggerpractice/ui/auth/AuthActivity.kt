package com.example.daggerpractice.ui.auth

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.RequestManager
import com.example.daggerpractice.R
import com.example.daggerpractice.models.User
import com.example.daggerpractice.ui.main.MainActivity
import com.example.daggerpractice.viewmodels.ViewModelProvidersFactory
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_auth.*
import javax.inject.Inject

class AuthActivity : DaggerAppCompatActivity() {

    private lateinit var viewModel: AuthViewModel

    @Inject
    lateinit var viewModelProvidersFactory: ViewModelProvidersFactory

    @set:Inject
    var logo: Drawable? = null

    @Inject
    lateinit var requestManager: RequestManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        viewModel = ViewModelProviders.of(this, viewModelProvidersFactory).get(AuthViewModel::class.java)

        setLogo()

        login_button.setOnClickListener { attempLogin() }

        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.observeAuthState().observe(this, Observer<AuthResource<User>> {
            when(it.status) {
                AuthResource.Status.AUTHENTICATED -> {
                    showProgressBar(false)
                    onLoginSuccess()
                    Log.d("lolo", "LOGIN SUCCESS: ${it.data?.username}")
                }
                AuthResource.Status.LOADING -> {
                    showProgressBar(true)
                }
                AuthResource.Status.ERROR -> {
                    showProgressBar(false)
                    Toast.makeText(this, "${it.error?.message} \nEnter number between 1 to 10", Toast.LENGTH_SHORT).show()
                }
                AuthResource.Status.NOT_AUTHENTICATED -> {
                    showProgressBar(false)
                }
            }
        })
    }

    private fun onLoginSuccess() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showProgressBar(isVisible: Boolean) {
        progress_bar.isVisible = isVisible
    }

    private fun attempLogin() {
        val userId = user_id_input.text.toString()
        if(userId.isEmpty()) {
            return
        }

        viewModel.authenticateWithId(userId.toInt())
    }

    private fun setLogo() {
        requestManager.load(logo).into(login_logo)
    }
}