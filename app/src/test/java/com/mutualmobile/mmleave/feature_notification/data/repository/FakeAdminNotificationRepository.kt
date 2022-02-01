package com.mutualmobile.mmleave.feature_notification.data.repository

import com.mutualmobile.mmleave.feature_notification.domain.model.NotificationModel
import com.mutualmobile.mmleave.feature_notification.domain.repository.AdminNotificationRepository
import com.mutualmobile.mmleave.feature_pto.domain.model.MMUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeAdminNotificationRepository : AdminNotificationRepository {

    override suspend fun fetchNotificationsWhere(email: String?): Flow<List<NotificationModel?>> {
        val notificationList = listOf<NotificationModel>(
            NotificationModel(notify_to = "anmol.verma@mm.com"),
            NotificationModel(notify_to = "niket.jain@mm.com"),
            NotificationModel(notify_to = "ambar.pandey@mm.com"),
            NotificationModel(notify_to = "anmol.verma@mm.com"),
            NotificationModel(notify_to = "shubham.singh@mm.com"),
            NotificationModel(notify_to = "anmol.verma@mm.com"),
            NotificationModel(notify_to = "nikhil.chouhan@mm.com"),

        )
        return flow {
            emit(
                notificationList.filter {
                    it.notify_to == email
                }
            )
        }
    }

    override suspend fun approvePtoRequest(approvedNotification: NotificationModel) {
        TODO("Not yet implemented")
    }

    override suspend fun approvePtoRequestForAllOtherAdmin(approvedNotificationList: List<NotificationModel?>) {
        TODO("Not yet implemented")
    }

    override suspend fun rejectPtoRequestForAllOtherAdmin(rejectNotificationList: List<NotificationModel?>) {
        TODO("Not yet implemented")
    }

    override suspend fun rejectPtoRequest(rejectNotification: NotificationModel) {
        TODO("Not yet implemented")
    }

    override suspend fun fetchMMUserDetails(userEmailId: String?): Flow<MMUser?> {
        TODO("Not yet implemented")
    }

    override fun updateNotificationObjectWithNotificationId(notificationFirebaseDocsList: List<NotificationModel?>) {
        TODO("Not yet implemented")
    }
}