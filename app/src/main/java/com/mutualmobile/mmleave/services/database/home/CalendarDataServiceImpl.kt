package com.mutualmobile.mmleave.services.database.home

import com.mutualmobile.mmleave.di.FirebaseModule
import com.mutualmobile.mmleave.data.model.CalendarPtoRequest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

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