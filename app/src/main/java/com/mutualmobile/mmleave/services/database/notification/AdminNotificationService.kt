package com.mutualmobile.mmleave.services.database.notification

import com.mutualmobile.mmleave.data.model.MMUser
import com.mutualmobile.mmleave.data.model.NotificationModel
import kotlinx.coroutines.flow.Flow

interface AdminNotificationService {
    suspend fun fetchNotificationsWhere(adminEmailId : String?) : Flow<List<NotificationModel?>>
    suspend fun approvePtoRequest(notificationModel: NotificationModel)
    suspend fun approvePtoRequestForAllOtherAdmin(notificationModel : List<NotificationModel?>)
    suspend fun rejectPtoRequestForAllOtherAdmin(notificationModel : List<NotificationModel?>)
    suspend fun rejectPtoRequest(notificationModel: NotificationModel)
    suspend fun fetchMMUserDetails(email : String?) : Flow<MMUser?>
    fun updateNotificationObjectWithNotificationId(notificationModel: List<NotificationModel?>)
}