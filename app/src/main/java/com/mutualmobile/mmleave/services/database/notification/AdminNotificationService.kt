package com.mutualmobile.mmleave.services.database.notification

import com.mutualmobile.mmleave.data.model.NotificationModel
import kotlinx.coroutines.flow.Flow

interface AdminNotificationService {
    suspend fun fetchNotificationsWhere(adminEmailId : String?) : Flow<List<NotificationModel?>>
    suspend fun approvePtoRequest(notificationModel: NotificationModel)
    suspend fun rejectPtoRequest(notificationModel: NotificationModel)
}