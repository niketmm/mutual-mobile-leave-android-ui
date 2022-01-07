package com.mutualmobile.mmleave.services.database.ptorequest

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mutualmobile.mmleave.data.model.MMUser
import com.mutualmobile.mmleave.firestore.PtoProperties
import com.mutualmobile.mmleave.services.auth.firebase.await
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.onFailure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow

@ExperimentalCoroutinesApi
class PtoRequestServiceImpl : PtoRequestService {

    val TAG = PtoRequestServiceImpl::class.simpleName

    override suspend fun makePtoRequest(
        ptoProperties: PtoProperties
    ) {
        val pto = HashMap<String, Any?>()
        pto["dateFrom"] = ptoProperties.dateFrom
        pto["dateTo"] = ptoProperties.dateTo
        pto["description"] = ptoProperties.description
        pto["email"] = ptoProperties.email
        FirebaseFirestore.getInstance().collection("PTOs_list").add(pto).await()
    }

    override suspend fun approvePtoRequest(
        ptoProperties: PtoProperties
    ) {
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        val userListCollectionRef = FirebaseFirestore.getInstance().collection("PTOs_list")

        firebaseUser?.email?.let { email ->
            userListCollectionRef.whereArrayContains("approver", email).get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        Log.d(TAG, "${document.id} => ${document.data}")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents: ", exception)
                }
        }
    }

    override suspend fun fetchAdminList() = callbackFlow {
        val listenerRegistration = FirebaseFirestore
            .getInstance()
            .collection("users_list")
            .whereEqualTo("userType", 1).addSnapshotListener { admins, error ->
                admins?.documents?.map { doc -> doc.toObject(MMUser::class.java) }?.let {
                    Log.d(TAG, "fetchAdminList: ${it.toString()}")
                    trySend(it).onFailure {
                    }
                }
            }
        awaitClose {
            listenerRegistration.remove()
        }
    }

    override suspend fun fetchUsersByUsername(username: String) = callbackFlow {
        val listenerRegistration = FirebaseFirestore.getInstance()
            .collection("users_list")
            .whereEqualTo("displayName", "Niket Jain")
            .addSnapshotListener { filteredAdmins, error ->
                filteredAdmins?.let {
                    filteredAdmins.documents.map { doc ->
                        doc.toObject(MMUser::class.java)
                    }.let {
                        Log.d(TAG, "fetchUsersByUsername: ${it.toString()}")
                        trySend(it).onFailure {
                        }
                    }
                }
            }

        awaitClose {
            listenerRegistration.remove()
        }
    }
}