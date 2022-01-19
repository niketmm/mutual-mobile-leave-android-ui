package com.mutualmobile.mmleave.services.database.home

import com.mutualmobile.mmleave.data.model.DisplayDateModel
import com.mutualmobile.mmleave.data.model.FirebasePtoRequestModel
import com.mutualmobile.mmleave.data.model.MMUser
import kotlinx.coroutines.flow.Flow

interface CalendarDataService {
    suspend fun fetchUserDatesList() : Flow<List<FirebasePtoRequestModel?>>
    suspend fun fetchUserDetails(email : String?) : Flow<MMUser?>
    suspend fun fetchHolidays() : Flow<List<DisplayDateModel>>
}