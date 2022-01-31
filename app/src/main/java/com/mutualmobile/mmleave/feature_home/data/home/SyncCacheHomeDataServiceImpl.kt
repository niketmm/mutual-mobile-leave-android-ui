package com.mutualmobile.mmleave.feature_home.data.home

import com.google.firebase.auth.FirebaseAuth
import com.mutualmobile.mmleave.data.data_store.StoreUserInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SyncCacheHomeDataServiceImpl @Inject constructor(
    private val userInfo: StoreUserInfo
) : SyncCacheHomeDataService {

    override suspend fun syncCacheWithDb(
        isUserAuthenticated : Boolean,
        totalPtoRequestLeft : Int,
        isUserAdmin : Boolean
    ) {
        // Caching the Response Synced With the Data base
        userInfo.setUserTotalPto(totalPtoLeavesLeft = totalPtoRequestLeft)
        userInfo.setIsUserAdminState(isAdmin = isUserAdmin)
        userInfo.setUserAuthenticateState(isUserAuthenticated)
    }

    override suspend fun getTotalPtoLeftCache(): Flow<Int> {
       return userInfo.getUserTotalPto
    }

    override suspend fun isUserAdmin(): Flow<Boolean?> {
        return userInfo.getIsUserAdminState
    }

    override suspend fun logoutUser() {
        userInfo.setIsUserAdminState(false)
        userInfo.setUserTotalPto(0)
        userInfo.setUserAuthenticateState(false)
        FirebaseAuth.getInstance().signOut()
        // Todo Google Sign Out
    }
}