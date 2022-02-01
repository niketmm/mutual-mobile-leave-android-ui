package com.mutualmobile.mmleave.feature_availed.domain.usecases

import com.mutualmobile.mmleave.feature_availed.domain.repository.PtoAvailedRepository
import javax.inject.Inject

class GetAllPtoAvailedUseCase @Inject constructor(
    private val ptoAvailedRepository: PtoAvailedRepository
) {

    suspend operator fun invoke() = ptoAvailedRepository.fetchAllPtoRequests()
}