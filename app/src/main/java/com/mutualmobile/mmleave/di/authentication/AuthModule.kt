package com.mutualmobile.mmleave.di.authentication

import com.mutualmobile.mmleave.feature_auth.data.repository.AuthenticationRepositoryImpl
import com.mutualmobile.mmleave.feature_auth.domain.repository.AuthenticationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthRepoModule(){

    /** Injecting the AuthRepo Interface **/
    @Binds
    @Singleton
    abstract fun provideAuthRepo(authenticationRepositoryImpl: AuthenticationRepositoryImpl) : AuthenticationRepository
}


