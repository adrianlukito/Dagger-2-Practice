package com.example.daggerpractice.ui.main.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.daggerpractice.SessionManager
import com.example.daggerpractice.models.User
import com.example.daggerpractice.ui.auth.AuthResource
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val sessionManager: SessionManager
): ViewModel() {
    init {
        Log.d("lolo", "profile viewModel is ready ")
    }

    fun getAuthenticatedUser(): LiveData<AuthResource<User>> {
        return sessionManager.getAuthUser()
    }
}