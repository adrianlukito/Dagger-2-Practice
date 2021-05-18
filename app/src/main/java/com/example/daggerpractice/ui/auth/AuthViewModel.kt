package com.example.daggerpractice.ui.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.daggerpractice.SessionManager
import com.example.daggerpractice.models.User
import com.example.daggerpractice.network.auth.AuthApi
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.function.Function
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val authApi: AuthApi,
    private val sessionManager: SessionManager
): ViewModel() {

    init {
        Log.d("lolo", ": view model is working $authApi")

    }

    fun authenticateWithId(userId: Int) {
        Log.d("lolo", "attempting login...")
        sessionManager.authenticateWithId(queryUserId(userId))
    }

    private fun queryUserId(userId: Int): LiveData<AuthResource<User>> {
        return LiveDataReactiveStreams.fromPublisher(
            authApi.getUser(userId)
                .map {
                    AuthResource.authenticated(it)
                }.onErrorReturn {
                    AuthResource.error(it, null)
                }.subscribeOn(Schedulers.io())
        )
    }

    fun observeAuthState(): LiveData<AuthResource<User>> {
        return sessionManager.getAuthUser()
    }
}