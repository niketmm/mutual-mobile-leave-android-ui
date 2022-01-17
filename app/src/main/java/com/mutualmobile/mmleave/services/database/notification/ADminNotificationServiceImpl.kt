package com.mutualmobile.mmleave.services.database.notification

import com.mutualmobile.mmleave.data.model.NotificationModel
import com.mutualmobile.mmleave.di.FirebaseModule
import com.mutualmobile.mmleave.screens.home.toLocalDate
import com.mutualmobile.mmleave.util.Constants
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

@ExperimentalCoroutinesApi
class AdminNotificationServiceImpl() : AdminNotificationService {

    override suspend fun fetchNotifications() = callbackFlow {
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

    override suspend fun approvePtoRequest(notificationModel: NotificationModel) {
        // Update the Users Collection with new Status
        val approvedHash = HashMap<String,Any>()
        approvedHash["approvedBy"] = notificationModel.notify_to.toString()
        approvedHash["ptoStatus"] = "APPROVED"
        notificationModel.datesList.forEach {
            FirebaseModule.provideFirebaseUserCollectionReference()
                .document(notificationModel.notify_to!!)
                .collection(Constants.PTO_LIST_COLLECTION)
                .document(it.toLocalDate().toString())
                .update(approvedHash)
        }
    }

    override suspend fun rejectPtoRequest(notificationModel: NotificationModel) {
        // Update the Users Collection with new Status
        val rejectedHash = HashMap<String,Any>()
        rejectedHash["rejectedBy"] = notificationModel.notify_to.toString()
        rejectedHash["ptoStatus"] = "REJECTED"
        notificationModel.datesList.forEach {
            FirebaseModule.provideFirebaseUserCollectionReference()
                .document(notificationModel.notify_to!!)
                .collection(Constants.PTO_LIST_COLLECTION)
                .document(it.toLocalDate().toString())
                .update(rejectedHash)
        }
    }
}



