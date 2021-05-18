package com.example.daggerpractice.di

import androidx.lifecycle.ViewModelProvider
import com.example.daggerpractice.viewmodels.ViewModelProvidersFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelProvidersFactory: ViewModelProvidersFactory): ViewModelProvider.Factory
}