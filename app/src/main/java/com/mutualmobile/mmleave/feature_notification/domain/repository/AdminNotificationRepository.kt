package com.mutualmobile.mmleave.feature_notification.domain.repository

import com.mutualmobile.mmleave.feature_pto.domain.model.MMUser
import com.mutualmobile.mmleave.feature_notification.domain.model.NotificationModel
import kotlinx.coroutines.flow.Flow

interface AdminNotificationRepository  {
    suspend fun fetchNotificationsWhere(email : String?) : Flow<List<NotificationModel?>>
    suspend fun approvePtoRequest(approvedNotification: NotificationModel)
    suspend fun approvePtoRequestForAllOtherAdmin(approvedNotificationList : List<NotificationModel?>)
    suspend fun rejectPtoRequestForAllOtherAdmin(rejectNotificationList : List<NotificationModel?>)
    suspend fun rejectPtoRequest(rejectNotification: NotificationModel)
    suspend fun fetchMMUserDetails(userEmailId : String?) : Flow<MMUser?>
    fun updateNotificationObjectWithNotificationId(notificationFirebaseDocsList : List<NotificationModel?>)
}