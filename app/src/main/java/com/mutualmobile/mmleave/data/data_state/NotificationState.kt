package com.mutualmobile.mmleave.data.data_state

import com.mutualmobile.mmleave.data.model.NotificationModel

data class NotificationState(
    var notificationList : List<NotificationModel?> = emptyList()
)