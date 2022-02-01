package com.mutualmobile.mmleave.feature_pto.data.repo

import com.mutualmobile.mmleave.local_cached_data.StoreUserInfo
import com.mutualmobile.mmleave.feature_pto.domain.model.MMUser
import com.mutualmobile.mmleave.feature_notification.domain.model.NotificationModel
import com.mutualmobile.mmleave.feature_pto.domain.model.PtoRequestDomain
import com.mutualmobile.mmleave.feature_pto.presentation.PtoRequestEvents
import com.mutualmobile.mmleave.feature_pto.data.service.NotificationRequester
import com.mutualmobile.mmleave.feature_pto.data.service.PtoRequestService
import com.mutualmobile.mmleave.feature_pto.domain.repository.ApplyPtoRepository
import com.mutualmobile.mmleave.feature_pto.data.service.SearchUserService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ApplyPtoRepositoryImpl @Inject constructor(
    private val notificationRequester: NotificationRequester,
    private val ptoRequestService: PtoRequestService,
    private val userStoreUserInfo: StoreUserInfo,
    private val searchUserService: SearchUserService
) : ApplyPtoRepository {

    override suspend fun makePtoRequest(
        ptoRequests: List<PtoRequestDomain?>,
        selectedAdmins: List<String?>
    ): Flow<PtoRequestEvents> {
        return ptoRequestService.makePtoRequest(ptoRequests = ptoRequests, selectedAdmins = selectedAdmins)
    }

    override suspend fun updateUserPtoDetails(leaveLeft: Int) {
       ptoRequestService.updateUserPtoDetails(leaveLeft)
    }

    override suspend fun approvePtoRequest(ptoRequestDomain: PtoRequestDomain) {
       ptoRequestService.approvePtoRequest(ptoRequestDomain)
    }

    override suspend fun sendNotificationToAdmin(notificationModel: NotificationModel) {
        notificationRequester.saveNotification(notificationModel = notificationModel)
    }

    override suspend fun getUserCachedPtoLeft() = userStoreUserInfo.getUserTotalPto

    override suspend fun setUserCachedPtoLeft(ptoLeft: Int) {
        userStoreUserInfo.setUserTotalPto(ptoLeft)
    }

    override suspend fun fetchAdminList(): Flow<List<MMUser?>> {
        return searchUserService.fetchAdminList()
    }

    override suspend fun fetchAllUserList(): Flow<List<MMUser?>> {
        return searchUserService.fetchAllUserList()
    }

    override suspend fun fetchUsersByUsername(username: String): Flow<List<MMUser?>> {
        return searchUserService.fetchUsersByUsername(username)
    }
}