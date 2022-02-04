package com.mutualmobile.mmleave.feature_availed.data.repository

import com.mutualmobile.mmleave.feature_availed.domain.repository.PtoAvailedRepository
import com.mutualmobile.mmleave.feature_pto.domain.model.FirebasePtoRequestModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeAvailedRepositoryImpl : PtoAvailedRepository {

    override suspend fun fetchAllPtoRequests(): Flow<List<FirebasePtoRequestModel?>> {
        return flow {
            val ptoAppliedList = listOf(
                FirebasePtoRequestModel(approvedBy = "niket@jain.com"),
                FirebasePtoRequestModel(approvedBy = "anmol@verma.com"),
                FirebasePtoRequestModel(approvedBy = "saurabh@mishra.com"),
                FirebasePtoRequestModel(approvedBy = "ambar@pandey.com"),
                FirebasePtoRequestModel(approvedBy = "shubham@singh.com"),
                FirebasePtoRequestModel(approvedBy = "nikhil@chouhan.com")
            )
            emit(ptoAppliedList)
        }
    }

    override fun getTotalPtoLeft(): Flow<Int> {
       return flow {
           emit(16)
       }
    }
}