package com.mutualmobile.mmleave.feature_notification.data.repository

import com.mutualmobile.mmleave.feature_pto.domain.model.MMUser
import com.mutualmobile.mmleave.feature_notification.domain.model.NotificationModel
import com.mutualmobile.mmleave.feature_notification.data.notification.AdminNotificationService
import com.mutualmobile.mmleave.feature_notification.domain.repository.AdminNotificationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AdminNotificationRepositoryImpl @Inject constructor(
    private val adminNotificationService: AdminNotificationService
): AdminNotificationRepository {

    override suspend fun fetchNotificationsWhere(email: String?): Flow<List<NotificationModel?>> {
        return adminNotificationService.fetchNotificationsWhere(email)
    }

    override suspend fun approvePtoRequest(approvedNotification: NotificationModel) {
        return adminNotificationService.approvePtoRequest(approvedNotification)
    }

    override suspend fun approvePtoRequestForAllOtherAdmin(approvedNotificationList: List<NotificationModel?>) {
        return adminNotificationService.approvePtoRequestForAllOtherAdmin(approvedNotificationList)
    }

    override suspend fun rejectPtoRequestForAllOtherAdmin(rejectNotificationList: List<NotificationModel?>) {
        return adminNotificationService.rejectPtoRequestForAllOtherAdmin(rejectNotificationList)
    }

    override suspend fun rejectPtoRequest(rejectNotification: NotificationModel) {
        return adminNotificationService.rejectPtoRequest(rejectNotification)
    }

    override suspend fun fetchMMUserDetails(userEmailId: String?): Flow<MMUser?> {
        return adminNotificationService.fetchMMUserDetails(userEmailId)
    }

    override fun updateNotificationObjectWithNotificationId(notificationFirebaseDocsList: List<NotificationModel?>) {
       return adminNotificationService.updateNotificationObjectWithNotificationId(notificationFirebaseDocsList)
    }

}