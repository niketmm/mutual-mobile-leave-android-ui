package com.mutualmobile.mmleave.feature_availed.domain.repository

import com.mutualmobile.mmleave.data.model.FirebasePtoRequestModel
import kotlinx.coroutines.flow.Flow

interface PtoAvailedRepository  {
    suspend fun fetchAllPtoRequests() : Flow<List<FirebasePtoRequestModel?>>
    fun getTotalPtoLeft() : Flow<Int>
}