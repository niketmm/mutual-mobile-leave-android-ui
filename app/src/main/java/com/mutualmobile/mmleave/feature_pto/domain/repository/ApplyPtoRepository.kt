package com.mutualmobile.mmleave.feature_pto.domain.repository

import com.mutualmobile.mmleave.data.model.MMUser
import com.mutualmobile.mmleave.data.model.NotificationModel
import com.mutualmobile.mmleave.data.model.PtoRequestDomain
import com.mutualmobile.mmleave.data.ui_event.PtoRequestEvents
import kotlinx.coroutines.flow.Flow

interface ApplyPtoRepository {

    suspend fun makePtoRequest(ptoRequests: List<PtoRequestDomain?>, selectedAdmins : List<String?>) : Flow<PtoRequestEvents>
    suspend fun updateUserPtoDetails(leaveLeft : Int)
    suspend fun approvePtoRequest(ptoRequestDomain: PtoRequestDomain)
    suspend fun sendNotificationToAdmin(notificationModel: NotificationModel)
    suspend fun getUserCachedPtoLeft() : Flow<Int>
    suspend fun setUserCachedPtoLeft(ptoLeft : Int)
    suspend fun fetchAdminList() : Flow<List<MMUser?>>
    suspend fun fetchAllUserList() : Flow<List<MMUser?>>
    suspend fun fetchUsersByUsername(username : String) : Flow<List<MMUser?>>
}