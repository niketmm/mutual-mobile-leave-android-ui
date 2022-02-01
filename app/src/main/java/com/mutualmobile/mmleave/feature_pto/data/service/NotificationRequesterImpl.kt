package com.mutualmobile.mmleave.feature_pto.data.service

import com.mutualmobile.mmleave.feature_notification.domain.model.NotificationModel
import com.mutualmobile.mmleave.di.FirebaseModule
import javax.inject.Inject

class NotificationRequesterImpl @Inject constructor() : NotificationRequester {

    override suspend fun saveNotification(notificationModel: NotificationModel) {
        FirebaseModule.provideFirebaseNotificationCollectionReference()
            .document()
            .set(notificationModel)
    }
}