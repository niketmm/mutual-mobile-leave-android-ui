package com.mutualmobile.mmleave.di

import com.mutualmobile.mmleave.data.repo.AuthenticationRepository
import com.mutualmobile.mmleave.data.service.AuthenticationService
import com.mutualmobile.mmleave.data.service.FirebaseAuthenticationService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AuthenticationModule {

    @Provides
    @Singleton
    fun provideAuthenticationService() : AuthenticationService {
        return FirebaseAuthenticationService()
    }
}