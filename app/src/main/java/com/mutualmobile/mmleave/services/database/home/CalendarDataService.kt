package com.mutualmobile.mmleave.services.database.home

import com.mutualmobile.mmleave.firestore.SetGetPtoRequests
import com.mutualmobile.mmleave.model.CalendarPtoRequest
import kotlinx.coroutines.flow.Flow

interface CalendarDataService {
    suspend fun fetchUserDatesList() : Flow<List<CalendarPtoRequest?>>
}