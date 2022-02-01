package com.mutualmobile.mmleave.feature_pto.data.service

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.mutualmobile.mmleave.feature_pto.domain.model.MMUser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.onFailure
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class FirebaseSearchUserServiceImpl @Inject constructor(): SearchUserService {

    private val TAG = FirebaseSearchUserServiceImpl::class.java.name

    override suspend fun fetchAllUserList() = callbackFlow {
        val userListenerRegistration = FirebaseFirestore
            .getInstance()
            .collection("users_list")
            .addSnapshotListener { admins, error ->
                admins?.documents?.map { doc -> doc.toObject(MMUser::class.java) }?.let {
                    Log.d(TAG, "fetchAdminList: $it")
                    trySend(it).onFailure {
                    }
                }
            }

        awaitClose{
            userListenerRegistration.remove()
        }
    }

    override suspend fun fetchAdminList() = callbackFlow {
        val adminListenerRegistration = FirebaseFirestore
            .getInstance()
            .collection("users_list")
            .whereEqualTo("userType", 1).addSnapshotListener { admins, error ->
                admins?.documents?.map { doc -> doc.toObject(MMUser::class.java) }?.let {
                    Log.d(TAG, "fetchAdminList: $it")
                    trySend(it).onFailure {
                    }
                }
            }
        awaitClose {
            adminListenerRegistration.remove()
        }
    }

    override suspend fun fetchUsersByUsername(username: String) = callbackFlow {
        val usernameListenerRegistration = FirebaseFirestore.getInstance()
            .collection("users_list")
            .whereArrayContains("nameAsArray", username)
            .addSnapshotListener { filteredAdmins, error ->
                filteredAdmins?.let {
                    filteredAdmins.documents.map { doc ->
                        doc.toObject(MMUser::class.java)
                    }.let {
                        Log.d(TAG, "fetchUsersByUsername: $it")
                        trySend(it).onFailure {
                        }
                    }
                }
            }

        awaitClose {
            usernameListenerRegistration.remove()
        }
    }
}