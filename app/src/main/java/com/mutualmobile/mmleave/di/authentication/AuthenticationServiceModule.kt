package com.mutualmobile.mmleave.di.authentication

import com.mutualmobile.mmleave.feature_auth.data.data_source.auth.firebase.AuthenticationService
import com.mutualmobile.mmleave.feature_auth.data.data_source.auth.firebase.FirebaseAuthenticationService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthenticationServiceModule {

    @Binds
    abstract fun provideAuthenticationService(
        firebaseAuthenticationService: FirebaseAuthenticationService
    ) : AuthenticationService
}