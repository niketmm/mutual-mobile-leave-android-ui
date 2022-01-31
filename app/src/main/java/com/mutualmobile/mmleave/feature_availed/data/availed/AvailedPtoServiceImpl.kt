package com.mutualmobile.mmleave.feature_availed.data.availed

import android.util.Log
import com.google.firebase.firestore.Query
import com.mutualmobile.mmleave.data.model.FirebasePtoRequestModel
import com.mutualmobile.mmleave.di.FirebaseModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class AvailedPtoServiceImpl @Inject constructor(): AvailedPtoService {

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
}