package com.mutualmobile.mmleave.feature_pto.domain.usecase

data class ApplyPtoUseCases(
    val approvePtoRequestUseCase: ApprovePtoRequestUseCase,
    val makePtoRequestUseCase: MakePtoRequestUseCase,
    val sendNotificationToAdminUseCase: SendNotificationToAdminUseCase,
    val updateUserPtoDetailsUseCase: UpdateUserPtoDetailsUseCase,
    val setUserPtoLeftUseCase: SetUserPtoLeftUseCase,
    val getUserPtoLeftUseCase: GetUserPtoLeftUseCase,
    val fetchAllUserListUseCase: FetchAllUserListUseCase,
    val fetchUsersByUsernameUseCase: FetchUsersByUsernameUseCase,
    val fetchAdminListUseCase: FetchAdminListUseCase
)