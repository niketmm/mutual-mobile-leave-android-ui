package com.mutualmobile.mmleave.feature_home.domain

import com.mutualmobile.mmleave.data.data_store.StoreUserInfo
import com.mutualmobile.mmleave.data.model.DisplayDateModel
import com.mutualmobile.mmleave.data.model.FirebasePtoRequestModel
import com.mutualmobile.mmleave.data.model.MMUser
import com.mutualmobile.mmleave.feature_home.data.home.HomeCalendarDataService
import com.mutualmobile.mmleave.feature_home.data.home.SyncCacheHomeDataService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeCalendarDataService: HomeCalendarDataService,
    private val syncCacheHomeDataService: SyncCacheHomeDataService
) : HomeRepository  {

    override suspend fun fetchUserDatesList(): Flow<List<FirebasePtoRequestModel?>> {
        return homeCalendarDataService.fetchUserDatesList()
    }

    override suspend fun fetchUserDetails(email: String?): Flow<MMUser?> {
        return homeCalendarDataService.fetchUserDetails(email = email)
    }

    override suspend fun fetchHolidays(): Flow<List<DisplayDateModel>> {
      return homeCalendarDataService.fetchHolidays()
    }

    override suspend fun fetchLatestPtoRequest(): Flow<FirebasePtoRequestModel> {
        return homeCalendarDataService.fetchLatestPtoRequest()
    }

    override suspend fun syncCacheWithDb(
        isUserAuthenticated : Boolean,
        totalPtoRequestLeft : Int,
        isUserAdmin : Boolean
    ) {
       syncCacheHomeDataService.syncCacheWithDb(isUserAuthenticated, totalPtoRequestLeft, isUserAdmin)
    }

    override suspend fun getTotalPtoLeftCache(): Flow<Int> {
       return syncCacheHomeDataService.getTotalPtoLeftCache()
    }

    override suspend fun isUserAdmin(): Flow<Boolean?> {
       return syncCacheHomeDataService.isUserAdmin()
    }

    override suspend fun logoutUser() {
       syncCacheHomeDataService.logoutUser()
    }
}