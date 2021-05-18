package com.example.daggerpractice

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.daggerpractice.models.User
import com.example.daggerpractice.ui.auth.AuthResource
import dagger.android.support.DaggerAppCompatActivity
import java.util.*
import javax.inject.Inject
import androidx.lifecycle.Observer
import com.example.daggerpractice.ui.auth.AuthActivity

abstract class BaseActivity: DaggerAppCompatActivity() {

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeObservers()
    }

    private fun subscribeObservers() {
        sessionManager.getAuthUser().observe(this, Observer {
            when(it.status) {
                AuthResource.Status.AUTHENTICATED -> {
                }
                AuthResource.Status.LOADING -> {
                }
                AuthResource.Status.ERROR -> {
                }
                AuthResource.Status.NOT_AUTHENTICATED -> {
                    navLoginScreen()
                }
            }
        })
    }

    private fun navLoginScreen() {
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }
}