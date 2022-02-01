package com.mutualmobile.mmleave.feature_home.data.home

import com.mutualmobile.mmleave.feature_home.domain.model.DisplayDateModel
import com.mutualmobile.mmleave.feature_pto.domain.model.FirebasePtoRequestModel
import com.mutualmobile.mmleave.feature_pto.domain.model.MMUser
import kotlinx.coroutines.flow.Flow

interface HomeCalendarDataService {
    suspend fun fetchUserDatesList() : Flow<List<FirebasePtoRequestModel?>>
    suspend fun fetchUserDetails(email : String?) : Flow<MMUser?>
    suspend fun fetchHolidays() : Flow<List<DisplayDateModel>>
    suspend fun fetchLatestPtoRequest() : Flow<FirebasePtoRequestModel>
}