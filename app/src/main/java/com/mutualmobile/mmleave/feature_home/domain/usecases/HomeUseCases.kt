package com.mutualmobile.mmleave.feature_home.domain.usecases

data class HomeUseCases(
    val getHolidaysUseCase: GetHolidaysUseCase,
    val getIsUserAdminUseCase: GetIsUserAdminUseCase,
    val getLatestPtoRequestUseCase : GetLatestPtoRequest,
    val getTotalCachedPtoUseCase : GetTotalCachedPto,
    val getUserDatesListUseCase: GetUserDatesListUseCase,
    val getUserDetailsUseCase: GetUserDetailsUseCase,
    val syncCacheUseCase: SyncCacheUseCase,
    val logoutUseCase: LogoutUseCase
)
