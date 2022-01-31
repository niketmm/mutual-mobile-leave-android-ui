package com.mutualmobile.mmleave.di.usecase

import com.mutualmobile.mmleave.feature_auth.domain.repository.AuthenticationRepository
import com.mutualmobile.mmleave.feature_auth.domain.usecase.AuthenticateUseCases
import com.mutualmobile.mmleave.feature_auth.domain.usecase.GoogleAuthenticateUserUseCase
import com.mutualmobile.mmleave.feature_auth.domain.usecase.SignInUserUseCase
import com.mutualmobile.mmleave.feature_auth.domain.usecase.UserExistsUseCase
import com.mutualmobile.mmleave.feature_availed.domain.repository.PtoAvailedRepository
import com.mutualmobile.mmleave.feature_availed.domain.usecases.GetAllPtoAvailedUseCase
import com.mutualmobile.mmleave.feature_availed.domain.usecases.GetTotalPtoLeftUseCase
import com.mutualmobile.mmleave.feature_availed.domain.usecases.PtoAvailedUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object UseCaseModule {

    @Provides
    @ActivityRetainedScoped
    fun provideAuthenticationUseCases(repository: AuthenticationRepository) : AuthenticateUseCases {
        return AuthenticateUseCases(
            signInUserUseCase = SignInUserUseCase(repository),
            userExistsUseCase = UserExistsUseCase(repository),
            googleAuthenticateUserUseCase = GoogleAuthenticateUserUseCase(repository)
        )
    }

    @Provides
    @ActivityRetainedScoped
    fun provideAvailedPtoUseCases(repository : PtoAvailedRepository) : PtoAvailedUseCase {
        return PtoAvailedUseCase(
            getAllPtoAvailedUseCase = GetAllPtoAvailedUseCase(repository),
            getTotalPtoLeftUseCase = GetTotalPtoLeftUseCase(repository)
        )
    }
}