package com.mutualmobile.mmleave.feature_pto.data.service

import com.mutualmobile.mmleave.feature_notification.domain.model.NotificationModel

interface NotificationRequester {
    suspend fun saveNotification(notificationModel: NotificationModel)
}