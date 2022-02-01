package com.mutualmobile.mmleave.feature_pto.domain.usecase

import com.mutualmobile.mmleave.feature_pto.domain.repository.ApplyPtoRepository
import javax.inject.Inject

class UpdateUserPtoDetailsUseCase @Inject constructor(
    private val applyPtoRepository: ApplyPtoRepository
) {

    suspend operator fun invoke(leaveLeft : Int) {
        applyPtoRepository.updateUserPtoDetails(leaveLeft)
    }
}