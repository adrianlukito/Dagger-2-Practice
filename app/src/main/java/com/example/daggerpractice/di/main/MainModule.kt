package com.example.daggerpractice.di.main

import com.example.daggerpractice.network.main.MainApi
import com.example.daggerpractice.ui.main.posts.PostsRecyclerAdapter
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class MainModule {

    @MainScope
    @Provides
    fun provideMainApi(retrofit: Retrofit): MainApi {
        return retrofit.create(MainApi::class.java)
    }

    @MainScope
    @Provides
    fun provideAdapter(): PostsRecyclerAdapter {
        return PostsRecyclerAdapter()
    }
}