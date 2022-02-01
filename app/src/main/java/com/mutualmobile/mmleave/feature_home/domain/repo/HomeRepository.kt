package com.mutualmobile.mmleave.feature_home.domain.repo

import com.mutualmobile.mmleave.feature_home.domain.model.DisplayDateModel
import com.mutualmobile.mmleave.feature_pto.domain.model.FirebasePtoRequestModel
import com.mutualmobile.mmleave.feature_pto.domain.model.MMUser
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun fetchUserDatesList() : Flow<List<FirebasePtoRequestModel?>>
    suspend fun fetchUserDetails(email : String?) : Flow<MMUser?>
    suspend fun fetchHolidays() : Flow<List<DisplayDateModel>>
    suspend fun fetchLatestPtoRequest() : Flow<FirebasePtoRequestModel>
    suspend fun syncCacheWithDb(
        isUserAuthenticated : Boolean,
        totalPtoRequestLeft : Int,
        isUserAdmin : Boolean
    )
    suspend fun getTotalPtoLeftCache() : Flow<Int>
    suspend fun isUserAdmin() : Flow<Boolean?>
    suspend fun logoutUser()
}