package com.mutualmobile.mmleave.di.authentication

import com.mutualmobile.mmleave.feature_auth.data.data_source.auth.firebase.AuthenticationService
import com.mutualmobile.mmleave.feature_auth.data.data_source.auth.social.GoogleSocialService
import com.mutualmobile.mmleave.feature_auth.data.repository.AuthenticationRepositoryImpl
import com.mutualmobile.mmleave.feature_auth.domain.repository.AuthenticationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object AuthModule {

    /** Injecting the AuthRepoImpl **/

    @Provides
    @ViewModelScoped
    fun provideAuthenticationRepository(
        socialService: GoogleSocialService,
        authenticationService: AuthenticationService
    ) : AuthenticationRepository {
        return AuthenticationRepositoryImpl(
            socialService = socialService,
            authenticationService = authenticationService
        )
    }
}