package com.mutualmobile.mmleave.feature_home.data.home

import android.util.Log
import com.google.firebase.firestore.Query
import com.mutualmobile.mmleave.feature_home.domain.model.DisplayDateModel
import com.mutualmobile.mmleave.di.FirebaseModule
import com.mutualmobile.mmleave.feature_pto.domain.model.FirebasePtoRequestModel
import com.mutualmobile.mmleave.feature_pto.domain.model.MMUser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class HomeCalendarDataServiceImpl @Inject constructor() : HomeCalendarDataService {

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

    override suspend fun fetchLatestPtoRequest() = callbackFlow {
        val listener = FirebaseModule.provideUserPtoRequestDocReference()
            .orderBy("date", Query.Direction.DESCENDING)
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