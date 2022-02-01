package com.mutualmobile.mmleave.feature_home.domain.usecases

import com.mutualmobile.mmleave.feature_home.domain.repo.HomeRepository
import javax.inject.Inject

class SyncCacheUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {

    suspend operator fun invoke(
        isUserAuthenticated : Boolean,
        totalPtoRequestLeft : Int,
        isUserAdmin : Boolean
    ) = homeRepository.syncCacheWithDb(
        isUserAdmin = isUserAdmin,
        totalPtoRequestLeft = totalPtoRequestLeft,
        isUserAuthenticated = isUserAuthenticated
    )
}