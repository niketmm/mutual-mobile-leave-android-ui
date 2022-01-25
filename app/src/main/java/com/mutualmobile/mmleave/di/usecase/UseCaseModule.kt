package com.mutualmobile.mmleave.di.usecase

import com.mutualmobile.mmleave.feature_auth.domain.repository.AuthenticationRepository
import com.mutualmobile.mmleave.feature_auth.domain.usecase.AuthenticateUseCases
import com.mutualmobile.mmleave.feature_auth.domain.usecase.GoogleAuthenticateUserUseCase
import com.mutualmobile.mmleave.feature_auth.domain.usecase.SignInUserUseCase
import com.mutualmobile.mmleave.feature_auth.domain.usecase.UserExistsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideAuthenticationUseCases(repository: AuthenticationRepository) : AuthenticateUseCases {
        return AuthenticateUseCases(
            signInUserUseCase = SignInUserUseCase(repository),
            userExistsUseCase = UserExistsUseCase(repository),
            googleAuthenticateUserUseCase = GoogleAuthenticateUserUseCase(repository)
        )
    }
}