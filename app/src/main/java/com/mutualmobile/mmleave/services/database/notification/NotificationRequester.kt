package com.mutualmobile.mmleave.services.database.notification

import com.mutualmobile.mmleave.data.model.NotificationModel

interface NotificationRequester {
    suspend fun saveNotification(notificationModel: NotificationModel)
}