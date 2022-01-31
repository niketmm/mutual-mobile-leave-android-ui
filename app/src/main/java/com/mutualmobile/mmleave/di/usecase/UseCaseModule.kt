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
import com.mutualmobile.mmleave.feature_home.domain.HomeRepository
import com.mutualmobile.mmleave.feature_home.domain.usecases.GetHolidaysUseCase
import com.mutualmobile.mmleave.feature_home.domain.usecases.GetIsUserAdminUseCase
import com.mutualmobile.mmleave.feature_home.domain.usecases.GetLatestPtoRequest
import com.mutualmobile.mmleave.feature_home.domain.usecases.GetTotalCachedPto
import com.mutualmobile.mmleave.feature_home.domain.usecases.GetUserDatesListUseCase
import com.mutualmobile.mmleave.feature_home.domain.usecases.GetUserDetailsUseCase
import com.mutualmobile.mmleave.feature_home.domain.usecases.HomeUseCases
import com.mutualmobile.mmleave.feature_home.domain.usecases.LogoutUseCase
import com.mutualmobile.mmleave.feature_home.domain.usecases.SyncCacheUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlin.math.log

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

    @Provides
    @ActivityRetainedScoped
    fun provideHomeUseCases(repository: HomeRepository) : HomeUseCases {
        return HomeUseCases(
            getHolidaysUseCase = GetHolidaysUseCase(repository),
            getIsUserAdminUseCase = GetIsUserAdminUseCase(repository),
            getLatestPtoRequestUseCase = GetLatestPtoRequest(repository),
            getTotalCachedPtoUseCase = GetTotalCachedPto(repository),
            getUserDatesListUseCase = GetUserDatesListUseCase(repository),
            getUserDetailsUseCase = GetUserDetailsUseCase(repository),
            syncCacheUseCase = SyncCacheUseCase(repository),
            logoutUseCase = LogoutUseCase(repository)
        )
    }
}