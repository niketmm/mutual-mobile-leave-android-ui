package com.mutualmobile.mmleave.services.database.availed

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
}