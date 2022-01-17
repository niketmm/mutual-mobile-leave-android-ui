package com.mutualmobile.mmleave.services.database.notification

import com.mutualmobile.mmleave.data.model.NotificationModel
import kotlinx.coroutines.flow.Flow

interface AdminNotificationService {
    suspend fun fetchNotifications() : Flow<List<NotificationModel?>>
}