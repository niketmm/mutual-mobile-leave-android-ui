package com.mutualmobile.mmleave.feature_home.data.home

import kotlinx.coroutines.flow.Flow

interface SyncCacheHomeDataService {
    suspend fun syncCacheWithDb(
        isUserAuthenticated : Boolean,
        totalPtoRequestLeft : Int,
        isUserAdmin : Boolean
    )
    suspend fun getTotalPtoLeftCache() : Flow<Int>
    suspend fun isUserAdmin() : Flow<Boolean?>
    suspend fun logoutUser()
}