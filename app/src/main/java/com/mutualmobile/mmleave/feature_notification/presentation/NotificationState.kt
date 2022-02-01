package com.mutualmobile.mmleave.feature_notification.presentation

import com.mutualmobile.mmleave.feature_notification.domain.model.NotificationModel

data class NotificationState(
    var notificationList : List<NotificationModel?> = emptyList()
)