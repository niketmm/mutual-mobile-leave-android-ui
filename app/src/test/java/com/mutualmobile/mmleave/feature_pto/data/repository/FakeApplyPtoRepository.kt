package com.mutualmobile.mmleave.feature_pto.data.repository

import com.mutualmobile.mmleave.feature_notification.domain.model.NotificationModel
import com.mutualmobile.mmleave.feature_pto.domain.model.MMUser
import com.mutualmobile.mmleave.feature_pto.domain.model.PtoRequestDomain
import com.mutualmobile.mmleave.feature_pto.domain.repository.ApplyPtoRepository
import com.mutualmobile.mmleave.feature_pto.presentation.PtoRequestEvents
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeApplyPtoRepository : ApplyPtoRepository {

    override suspend fun makePtoRequest(
        ptoRequests: List<PtoRequestDomain?>,
        selectedAdmins: List<String?>
    ): Flow<PtoRequestEvents> {
        TODO("Not yet implemented")
    }

    override suspend fun updateUserPtoDetails(leaveLeft: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun approvePtoRequest(ptoRequestDomain: PtoRequestDomain) {
        TODO("Not yet implemented")
    }

    override suspend fun sendNotificationToAdmin(notificationModel: NotificationModel) {
        TODO("Not yet implemented")
    }

    override suspend fun getUserCachedPtoLeft(): Flow<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun setUserCachedPtoLeft(ptoLeft: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun fetchAdminList(): Flow<List<MMUser?>> {
        return flow {
            val mmUserList = listOf(
                MMUser(displayName = "niket", userType = 1),
                MMUser(displayName = "niket", userType = 2),
                MMUser(displayName = "niket", userType = 1),
                MMUser(displayName = "niket", userType = 0),
                MMUser(displayName = "niket", userType = 1),
                MMUser(displayName = "niket", userType = 0),
                MMUser(displayName = "niket", userType = 1),
            )

            emit(
                mmUserList.filter {
                    it.userType == 1
                }
            )
        }
    }

    override suspend fun fetchAllUserList(): Flow<List<MMUser?>> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchUsersByUsername(username: String): Flow<List<MMUser?>> {
        TODO("Not yet implemented")
    }
}