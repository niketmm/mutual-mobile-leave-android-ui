package com.mutualmobile.mmleave.feature_home.data.repository

import com.mutualmobile.mmleave.feature_home.domain.model.DisplayDateModel
import com.mutualmobile.mmleave.feature_home.domain.repo.HomeRepository
import com.mutualmobile.mmleave.feature_pto.domain.model.FirebasePtoRequestModel
import com.mutualmobile.mmleave.feature_pto.domain.model.MMUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*

class FakeHomeRepository : HomeRepository {

    override suspend fun fetchUserDatesList(): Flow<List<FirebasePtoRequestModel?>> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchUserDetails(email: String?): Flow<MMUser?> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchHolidays(): Flow<List<DisplayDateModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchLatestPtoRequest(): Flow<FirebasePtoRequestModel> {
       // Return the last item from the list
        return flow {
            val listOfPtoRequest = listOf(
                FirebasePtoRequestModel(date = com.google.firebase.Timestamp(Date(1000))),
                FirebasePtoRequestModel(date = com.google.firebase.Timestamp(Date(1005))),
                FirebasePtoRequestModel(date = com.google.firebase.Timestamp(Date(1008)))
            )
            emit(listOfPtoRequest.last())
        }
    }

    override suspend fun syncCacheWithDb(
        isUserAuthenticated: Boolean,
        totalPtoRequestLeft: Int,
        isUserAdmin: Boolean
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun getTotalPtoLeftCache(): Flow<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun isUserAdmin(): Flow<Boolean?> {
        TODO("Not yet implemented")
    }

    override suspend fun logoutUser() {
        TODO("Not yet implemented")
    }

}