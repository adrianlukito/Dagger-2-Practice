package com.example.daggerpractice.di.auth

import com.example.daggerpractice.network.auth.AuthApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create

@Module
class AuthModule {

    @AuthScope
    @Provides
    fun provideAuthApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }
}