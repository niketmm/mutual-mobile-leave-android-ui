package com.mutualmobile.mmleave.feature_pto.domain.usecase

import com.mutualmobile.mmleave.feature_pto.domain.model.PtoRequestDomain
import com.mutualmobile.mmleave.feature_pto.domain.repository.ApplyPtoRepository
import javax.inject.Inject

class MakePtoRequestUseCase @Inject constructor(
    private val applyPtoRepository: ApplyPtoRepository
) {
    suspend operator fun invoke(
        ptoRequestsList : List<PtoRequestDomain?>,
        selectedAdminsList  : List<String?>
    ) = applyPtoRepository.makePtoRequest(
        ptoRequests = ptoRequestsList,
        selectedAdmins = selectedAdminsList
    )
}