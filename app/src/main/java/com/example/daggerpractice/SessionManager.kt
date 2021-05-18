package com.example.daggerpractice

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.daggerpractice.models.User
import com.example.daggerpractice.ui.auth.AuthResource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor() {
    private val cacheUser = MediatorLiveData<AuthResource<User>>()

    fun authenticateWithId(source: LiveData<AuthResource<User>>) {
        cacheUser.value = AuthResource.loading(null)
        cacheUser.addSource(source) {
            cacheUser.value = it
            cacheUser.removeSource(source)
        }
    }

    fun logout() {
        Log.d("lolo", "logging out...")
        cacheUser.value = AuthResource.logout()
    }

    fun getAuthUser(): LiveData<AuthResource<User>> {
        return cacheUser
    }
}