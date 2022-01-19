package com.mutualmobile.mmleave.services.database.home

import com.mutualmobile.mmleave.data.model.DisplayDateModel
import com.mutualmobile.mmleave.di.FirebaseModule
import com.mutualmobile.mmleave.data.model.FirebasePtoRequestModel
import com.mutualmobile.mmleave.data.model.MMUser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class CalendarDataServiceImpl @Inject constructor() : CalendarDataService {

    override suspend fun fetchUserDatesList() = callbackFlow {
        val listeners = FirebaseModule.provideUserPtoRequestDocReference()
            .addSnapshotListener { ptoRequest, error ->
                ptoRequest?.documents?.map { doc ->
                    doc.toObject(FirebasePtoRequestModel::class.java)
                }?.let {
                    trySend(it)
                }
            }

        awaitClose {
            listeners.remove()
        }
    }

    override suspend fun fetchUserDetails(email: String?) = callbackFlow {
        val userDetailListener = FirebaseModule.provideFirebaseUserCollectionReference()
            .document(email.toString())
            .addSnapshotListener { user, error ->
                trySend(user?.toObject(MMUser::class.java))
            }

        awaitClose {
            userDetailListener.remove()
        }
    }

    override suspend fun fetchHolidays() = callbackFlow {
        val holidayListener = FirebaseModule.provideFirebaseHolidayCollection()
            .addSnapshotListener { holidays, error ->
                holidays?.map {
                    trySend(holidays.toObjects(DisplayDateModel::class.java))
                }
            }

        awaitClose {
            holidayListener.remove()
        }
    }
}