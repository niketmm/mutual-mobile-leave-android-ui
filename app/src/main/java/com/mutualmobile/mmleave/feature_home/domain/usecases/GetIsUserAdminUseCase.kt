package com.mutualmobile.mmleave.feature_home.domain.usecases

import com.mutualmobile.mmleave.feature_home.domain.repo.HomeRepository
import javax.inject.Inject

class GetIsUserAdminUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {

    suspend operator fun invoke() = homeRepository.isUserAdmin()
}