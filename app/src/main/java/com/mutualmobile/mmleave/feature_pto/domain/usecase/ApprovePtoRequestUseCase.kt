package com.mutualmobile.mmleave.feature_pto.domain.usecase

import com.mutualmobile.mmleave.data.model.PtoRequestDomain
import com.mutualmobile.mmleave.feature_pto.domain.repository.ApplyPtoRepository
import javax.inject.Inject

class ApprovePtoRequestUseCase @Inject constructor(
    private val applyPtoRepository: ApplyPtoRepository
) {

    suspend operator fun invoke(
        ptoRequest : PtoRequestDomain
    ) {
        applyPtoRepository.approvePtoRequest(ptoRequest)
    }
}