package com.mutualmobile.mmleave.services.database.availed

import android.util.Log
import com.google.firebase.firestore.Query
import com.mutualmobile.mmleave.data.model.FirebasePtoRequestModel
import com.mutualmobile.mmleave.di.FirebaseModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

@ExperimentalCoroutinesApi
class AvailedPtoServiceImpl : AvailedPtoService {

    override suspend fun fetchAllPtoRequests(): Flow<List<FirebasePtoRequestModel?>> = callbackFlow {
        val listener = FirebaseModule.provideUserPtoRequestDocReference()
            .addSnapshotListener { ptos, error ->
                ptos?.documents?.map {
                    it.toObject(FirebasePtoRequestModel::class.java)
                }?.let {
                    trySend(it)
                }
            }

        awaitClose {
            listener.remove()
        }
    }

    override suspend fun fetchLatestPtoRequests() = callbackFlow {
        val listener = FirebaseModule.provideUserPtoRequestDocReference()
            .orderBy("date",Query.Direction.DESCENDING)
            .limit(1)
            .get()
            .addOnCompleteListener { task ->
                task.result.forEach { doc ->
                    trySend(doc.toObject(FirebasePtoRequestModel::class.java))
                }
            }
            .addOnFailureListener {
                Log.d("LatestPtoRequest", "fetchLatestPtoRequests: $it")
            }

        awaitClose {
            listener.isComplete
        }
    }
}