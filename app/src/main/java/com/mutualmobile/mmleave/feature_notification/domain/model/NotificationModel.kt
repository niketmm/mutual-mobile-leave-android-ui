package com.mutualmobile.mmleave.feature_notification.domain.model

import com.google.firebase.Timestamp

data class NotificationModel(
    val datesList : List<Timestamp?> = emptyList(),
    val notify_to : String? = null,
    val notify_from : String? = null,
    val title : String? = null,
    val notify_type : Int? = 0,
    val notificationDocumentId : String? = null
)
