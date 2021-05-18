package com.example.daggerpractice.di

import com.example.daggerpractice.di.auth.AuthModule
import com.example.daggerpractice.di.auth.AuthScope
import com.example.daggerpractice.di.auth.AuthViewModelsModule
import com.example.daggerpractice.di.main.MainFragmentBuildersModule
import com.example.daggerpractice.di.main.MainModule
import com.example.daggerpractice.di.main.MainScope
import com.example.daggerpractice.di.main.MainViewModelsModule
import com.example.daggerpractice.ui.auth.AuthActivity
import com.example.daggerpractice.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @AuthScope
    @ContributesAndroidInjector(
        modules = [
            AuthViewModelsModule::class,
            AuthModule::class
        ]
    )
    abstract fun contributeAuthActivity(): AuthActivity

    @MainScope
    @ContributesAndroidInjector(
        modules = [
            MainFragmentBuildersModule::class,
            MainViewModelsModule::class,
            MainModule::class
        ]
    )
    abstract fun contributeMainActivity(): MainActivity
}
