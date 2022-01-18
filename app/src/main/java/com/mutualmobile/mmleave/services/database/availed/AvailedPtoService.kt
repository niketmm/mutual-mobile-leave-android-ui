package com.mutualmobile.mmleave.services.database.availed

import com.mutualmobile.mmleave.data.model.FirebasePtoRequestModel
import kotlinx.coroutines.flow.Flow

interface AvailedPtoService {
    suspend fun fetchAllPtoRequests() : Flow<List<FirebasePtoRequestModel?>>
    suspend fun fetchLatestPtoRequests() : Flow<FirebasePtoRequestModel?>
}