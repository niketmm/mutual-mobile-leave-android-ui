package com.mutualmobile.mmleave.feature_availed.domain.usecases

import com.mutualmobile.mmleave.feature_availed.domain.repository.PtoAvailedRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTotalPtoLeftUseCase @Inject constructor(
    private val ptoAvailedRepository: PtoAvailedRepository
) {

    operator fun invoke() = ptoAvailedRepository.getTotalPtoLeft()
}