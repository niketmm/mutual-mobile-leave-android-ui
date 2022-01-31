package com.mutualmobile.mmleave.feature_home.domain.usecases

import com.mutualmobile.mmleave.feature_home.domain.HomeRepository
import javax.inject.Inject

class GetHolidaysUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {

    suspend operator fun invoke() = homeRepository.fetchHolidays()
}