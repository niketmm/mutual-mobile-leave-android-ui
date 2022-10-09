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
import com.mutualmobile.mmleave.feature_home.domain.repo.HomeRepository
import com.mutualmobile.mmleave.feature_home.domain.usecases.GetHolidaysUseCase
import com.mutualmobile.mmleave.feature_home.domain.usecases.GetIsUserAdminUseCase
import com.mutualmobile.mmleave.feature_home.domain.usecases.GetLatestPtoRequest
import com.mutualmobile.mmleave.feature_home.domain.usecases.GetTotalCachedPto
import com.mutualmobile.mmleave.feature_home.domain.usecases.GetUserDatesListUseCase
import com.mutualmobile.mmleave.feature_home.domain.usecases.GetUserDetailsUseCase
import com.mutualmobile.mmleave.feature_home.domain.usecases.HomeUseCases
import com.mutualmobile.mmleave.feature_home.domain.usecases.LogoutUseCase
import com.mutualmobile.mmleave.feature_home.domain.usecases.SyncCacheUseCase
import com.mutualmobile.mmleave.feature_notification.domain.repository.AdminNotificationRepository
import com.mutualmobile.mmleave.feature_notification.domain.usecase.AdminNotificationUseCases
import com.mutualmobile.mmleave.feature_notification.domain.usecase.ApprovedSingleNotificationUseCase
import com.mutualmobile.mmleave.feature_notification.domain.usecase.FetchNotificationsListUseCase
import com.mutualmobile.mmleave.feature_notification.domain.usecase.FetchUserDetailsForNotificationDetailUseCase
import com.mutualmobile.mmleave.feature_notification.domain.usecase.RejectSingleNotificationUseCase
import com.mutualmobile.mmleave.feature_notification.domain.usecase.UpdateApprovedReqForAllAdmins
import com.mutualmobile.mmleave.feature_notification.domain.usecase.UpdateFirebaseNotificationDocsUseCases
import com.mutualmobile.mmleave.feature_notification.domain.usecase.UpdateRejectedReqForAllAdmins
import com.mutualmobile.mmleave.feature_pto.domain.repository.ApplyPtoRepository
import com.mutualmobile.mmleave.feature_pto.domain.usecase.ApplyPtoUseCases
import com.mutualmobile.mmleave.feature_pto.domain.usecase.ApprovePtoRequestUseCase
import com.mutualmobile.mmleave.feature_pto.domain.usecase.FetchAdminListUseCase
import com.mutualmobile.mmleave.feature_pto.domain.usecase.FetchAllUserListUseCase
import com.mutualmobile.mmleave.feature_pto.domain.usecase.FetchUsersByUsernameUseCase
import com.mutualmobile.mmleave.feature_pto.domain.usecase.GetUserPtoLeftUseCase
import com.mutualmobile.mmleave.feature_pto.domain.usecase.MakePtoRequestUseCase
import com.mutualmobile.mmleave.feature_pto.domain.usecase.SendNotificationToAdminUseCase
import com.mutualmobile.mmleave.feature_pto.domain.usecase.SetUserPtoLeftUseCase
import com.mutualmobile.mmleave.feature_pto.domain.usecase.UpdateUserPtoDetailsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object MockUseCasesModule {

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

    @Provides
    @ActivityRetainedScoped
    fun provideApplyPtoUseCases(repository: ApplyPtoRepository) : ApplyPtoUseCases {
        return ApplyPtoUseCases(
            approvePtoRequestUseCase = ApprovePtoRequestUseCase(repository),
            makePtoRequestUseCase = MakePtoRequestUseCase(repository),
            sendNotificationToAdminUseCase = SendNotificationToAdminUseCase(repository),
            updateUserPtoDetailsUseCase = UpdateUserPtoDetailsUseCase(repository),
            setUserPtoLeftUseCase = SetUserPtoLeftUseCase(repository),
            getUserPtoLeftUseCase =  GetUserPtoLeftUseCase(repository),
            fetchAdminListUseCase = FetchAdminListUseCase(repository),
            fetchAllUserListUseCase = FetchAllUserListUseCase(repository),
            fetchUsersByUsernameUseCase = FetchUsersByUsernameUseCase(repository)
        )
    }

    @Provides
    @ActivityRetainedScoped
    fun provideAdminNotificationUseCases(repository : AdminNotificationRepository) : AdminNotificationUseCases {
        return AdminNotificationUseCases(
            approvedSingleNotificationUseCase = ApprovedSingleNotificationUseCase(repository),
            fetchNotificationsListUseCase = FetchNotificationsListUseCase(repository),
            fetchUserDetailsForNotificationDetailUseCase = FetchUserDetailsForNotificationDetailUseCase(repository),
            rejectSingleNotificationUseCase = RejectSingleNotificationUseCase(repository),
            updateApprovedReqForAllAdmins = UpdateApprovedReqForAllAdmins(repository),
            updateFirebaseNotificationDocsUseCases = UpdateFirebaseNotificationDocsUseCases(repository),
            updateRejectedReqForAllAdmins = UpdateRejectedReqForAllAdmins(repository)
        )
    }
}