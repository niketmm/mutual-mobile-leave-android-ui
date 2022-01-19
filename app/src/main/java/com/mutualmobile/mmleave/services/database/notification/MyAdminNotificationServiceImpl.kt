package com.mutualmobile.mmleave.services.database.notification

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.SetOptions
import com.mutualmobile.mmleave.data.model.MMUser
import com.mutualmobile.mmleave.data.model.NotificationModel
import com.mutualmobile.mmleave.di.FirebaseModule
import com.mutualmobile.mmleave.screens.home.toLocalDate
import com.mutualmobile.mmleave.util.Constants
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MyAdminNotificationServiceImpl @Inject constructor() : AdminNotificationService {

    override suspend fun fetchNotificationsWhere(adminEmailId: String?) = callbackFlow {
        val listener = FirebaseModule.provideFirebaseNotificationCollectionReference()
            .whereEqualTo("notify_to", adminEmailId)
            .addSnapshotListener { notifications, error ->
                notifications?.documents?.map {
                    it.toObject(NotificationModel::class.java)?.copy(
                     notificationDocumentId = it.id
                    )
                }?.let { model ->
                    updateNotificationObjectWithNotificationId(model)
                    trySend(model)
                }
            }

        awaitClose {
            listener.remove()
        }
    }

    override suspend fun approvePtoRequest(notificationModel: NotificationModel) {
        // Update the Users Collection with new Status
        val approvedHash = HashMap<String, Any>()
        approvedHash["approvedBy"] = FirebaseModule.currentUser!!
        approvedHash["ptoStatus"] = "APPROVED"
        notificationModel.datesList.forEach {
            FirebaseModule.provideFirebaseUserCollectionReference()
                .document(notificationModel.notify_from!!)
                .collection(Constants.PTO_LIST_COLLECTION)
                .document(it.toLocalDate().toString())
                .update(approvedHash)
                .addOnSuccessListener {
                    Log.d("FirebaseNotificationReq", "approvePtoRequest: Successfully updated")
                }
                .addOnFailureListener {
                    Log.d("FirebaseNotificationReq", "approvePtoRequest: ${it.message}")
                }
        }
    }

    override suspend fun approvePtoRequestForAllOtherAdmin(notificationModel: List<NotificationModel?>) {
        // Update the Notification Type as well
        val approvedHash = HashMap<String, Any>()
        approvedHash["notify_type"] = 1
        notificationModel.forEach {
            FirebaseModule.provideFirebaseNotificationCollectionReference()
                .document(it?.notificationDocumentId.toString())
                .update(approvedHash)
                .addOnCompleteListener {
                    Log.d("approvePtoRequestForAllOtherAdmin", ": ${it.isComplete}")
                }
                .addOnFailureListener {
                    Log.d("approvePtoRequestForAllOtherAdmin", ": ${it.message}")
                }
        }
    }

    override suspend fun rejectPtoRequestForAllOtherAdmin(notificationModel: List<NotificationModel?>) {
        // Update the Notification Type as well
        val rejectedHash = HashMap<String, Any>()
        rejectedHash["notify_type"] = 0
        notificationModel.forEach {
            FirebaseModule.provideFirebaseNotificationCollectionReference()
                .document(it?.notificationDocumentId.toString())
                .update(rejectedHash)
                .addOnCompleteListener {
                    Log.d("approvePtoRequestForAllOtherAdmin", ": ${it.isComplete}")
                }
                .addOnFailureListener {
                    Log.d("approvePtoRequestForAllOtherAdmin", ": ${it.message}")
                }
        }
    }

    override suspend fun rejectPtoRequest(notificationModel: NotificationModel) {
        // Update the Users Collection with new Status
        val rejectedHash = HashMap<String, Any>()
        rejectedHash["rejectedBy"] = FirebaseModule.currentUser!!
        rejectedHash["ptoStatus"] = "REJECTED"
        notificationModel.datesList.forEach {
            FirebaseModule.provideFirebaseUserCollectionReference()
                .document(notificationModel.notify_from!!)
                .collection(Constants.PTO_LIST_COLLECTION)
                .document(it.toLocalDate().toString())
                .update(rejectedHash)
                .addOnSuccessListener {
                    Log.d("FirebaseNotificationReq", "approvePtoRequest: Successfully updated")
                }
                .addOnFailureListener {
                    Log.d("FirebaseNotificationReq", "approvePtoRequest: ${it.message}")
                }
        }

        // Update the Notification Type as well
        notificationModel.datesList.forEach {
            FirebaseModule.provideFirebaseNotificationCollectionReference()
                .whereArrayContains("datesList", it.toString())
                .whereEqualTo("notify_to", notificationModel.notify_to)
                .addSnapshotListener { value, error ->
                    value?.documents?.forEach { doc ->
                        FirebaseModule.provideFirebaseNotificationCollectionReference()
                            .document(doc.id)
                            .update("notify_type", 0)
                    }
                }
        }
    }

    override suspend fun fetchMMUserDetails(email: String?) = callbackFlow {
        email?.let {
            val listener = FirebaseModule.provideFirebaseUserCollectionReference()
                .document(it)
                .addSnapshotListener { mmUser, error ->
                    trySend(mmUser?.toObject(MMUser::class.java))
                }

            awaitClose{
                listener.remove()
            }
        }
    }

    override fun updateNotificationObjectWithNotificationId(notificationModel: List<NotificationModel?>) {
        notificationModel.forEach {
            val updateDocumentIdMap = HashMap<String,Any>()
            updateDocumentIdMap["notificationDocumentId"] = it?.notificationDocumentId ?: 0
            FirebaseModule.provideFirebaseNotificationCollectionReference()
                .document(it?.notificationDocumentId.toString())
                .update(updateDocumentIdMap)
                .addOnCompleteListener {
                    Log.d("updateNotificationObjectWithNotificationId", "NotificationDocument Updated")
                }
                .addOnFailureListener {
                    Log.d("updateNotificationObjectWithNotificationId", "NotificationDocument Cancelled due to ${it.message}")
                }
        }
    }
}



