package com.mutualmobile.mmleave.services.database.home

import com.mutualmobile.mmleave.data.model.CalendarPtoRequest
import kotlinx.coroutines.flow.Flow

interface CalendarDataService {
    suspend fun fetchUserDatesList() : Flow<List<CalendarPtoRequest?>>
}