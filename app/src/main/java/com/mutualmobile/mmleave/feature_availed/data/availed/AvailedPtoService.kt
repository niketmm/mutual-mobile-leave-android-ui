package com.mutualmobile.mmleave.feature_availed.data.availed

import com.mutualmobile.mmleave.data.model.FirebasePtoRequestModel
import kotlinx.coroutines.flow.Flow

interface AvailedPtoService {
    suspend fun fetchAllPtoRequests() : Flow<List<FirebasePtoRequestModel?>>
}