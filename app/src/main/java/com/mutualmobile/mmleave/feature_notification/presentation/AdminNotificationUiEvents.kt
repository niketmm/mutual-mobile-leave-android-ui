package com.mutualmobile.mmleave.feature_notification.presentation

import com.mutualmobile.mmleave.feature_notification.domain.model.NotificationModel

sealed class AdminNotificationUiEvents {
    data class ApprovedRequest(val notification : NotificationModel) : AdminNotificationUiEvents()
    data class RejectRequest(val notification: NotificationModel) : AdminNotificationUiEvents()
}