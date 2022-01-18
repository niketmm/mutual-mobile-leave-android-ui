package com.mutualmobile.mmleave.services.database.home

import com.mutualmobile.mmleave.data.model.FirebasePtoRequestModel
import kotlinx.coroutines.flow.Flow

interface CalendarDataService {
    suspend fun fetchUserDatesList() : Flow<List<FirebasePtoRequestModel?>>
}