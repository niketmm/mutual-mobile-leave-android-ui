package com.mutualmobile.mmleave.services.database.notification

import com.mutualmobile.mmleave.data.model.NotificationModel
import com.mutualmobile.mmleave.di.FirebaseModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

@ExperimentalCoroutinesApi
class AdminNotificationServiceImpl() : AdminNotificationService {

    override suspend fun fetchNotifications() = callbackFlow{
        val listener = FirebaseModule.provideFirebaseNotificationCollectionReference()
            .addSnapshotListener { notifications, error ->
                notifications?.documents?.map {
                  it.toObject(NotificationModel::class.java)
                }?.let { model ->
                    trySend(model)
                }
            }


        awaitClose {
            listener.remove()
        }
    }
}