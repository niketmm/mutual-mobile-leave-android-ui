package com.mutualmobile.mmleave.services.database.notification

import com.mutualmobile.mmleave.data.model.NotificationModel
import com.mutualmobile.mmleave.di.FirebaseModule
import javax.inject.Inject

class NotificationRequesterImpl @Inject constructor() : NotificationRequester {

    override suspend fun saveNotification(notificationModel: NotificationModel) {
        FirebaseModule.provideFirebaseNotificationCollectionReference()
            .document()
            .set(notificationModel)
    }
}