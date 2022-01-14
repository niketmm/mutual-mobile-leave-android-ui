package com.mutualmobile.mmleave.services.database.home

import com.google.firebase.firestore.CollectionReference
import com.mutualmobile.mmleave.di.FirebaseModule
import com.mutualmobile.mmleave.firestore.SetGetPtoRequests
import com.mutualmobile.mmleave.model.CalendarPtoRequest
import com.mutualmobile.mmleave.services.auth.firebase.await
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
class CalendarDataServiceImpl @Inject constructor() : CalendarDataService {

    override suspend fun fetchUserDatesList() = callbackFlow {
        val listeners = FirebaseModule.provideUserPtoRequestDocReference()
            .addSnapshotListener { ptoRequest, error ->
                ptoRequest?.documents?.map { doc ->
                    doc.toObject(CalendarPtoRequest::class.java)
                }?.let {
                    trySend(it)
                }
            }

        awaitClose {
            listeners.remove()
        }
    }
}