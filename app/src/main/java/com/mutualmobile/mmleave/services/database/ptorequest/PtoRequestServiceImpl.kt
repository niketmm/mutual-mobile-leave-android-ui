package com.mutualmobile.mmleave.services.database.ptorequest

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mutualmobile.mmleave.data.model.Admins
import com.mutualmobile.mmleave.di.FirebaseModule
import com.mutualmobile.mmleave.data.model.PtoRequestDomain
import com.mutualmobile.mmleave.data.ui_event.PtoRequestEvents
import kotlin.collections.HashMap
import com.mutualmobile.mmleave.util.Constants.PTO_LIST_COLLECTION
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import java.sql.Date
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

@ExperimentalCoroutinesApi
class PtoRequestServiceImpl @Inject constructor() : PtoRequestService {

    val TAG = PtoRequestServiceImpl::class.simpleName

    override suspend fun makePtoRequest(
        ptoRequests: List<PtoRequestDomain?>,
        selectedAdmins: List<Admins?>
    ) = callbackFlow {
        ptoRequests.forEach {
            val listener = FirebaseModule.provideUserPtoRequestDocReference()
                .document(it?.date.toString())
                .set(getPtoMap(ptoRequest = it, selectedAdmins = selectedAdmins))
                .addOnSuccessListener {
                    trySend(PtoRequestEvents.Success)
                }
                .addOnFailureListener { exception ->
                    trySend(
                        PtoRequestEvents.Failed(
                            exception.message ?: "Couldn't able to save the info"
                        )
                    )
                }

            awaitClose {
                listener.isCanceled
            }
        }

        awaitClose {
            // Todo Better way to handle Events
        }
    }

    private fun getPtoMap(
        ptoRequest: PtoRequestDomain?,
        selectedAdmins: List<Admins?>
    ): HashMap<String, Any?> {
        val ptoMap = HashMap<String, Any?>()
        ptoMap["description"] = ptoRequest?.description
        ptoMap["email"] = ptoRequest?.email
        ptoMap["date"] = ptoRequest?.date?.toFirebaseTimestamp()
        ptoMap["ptoStatus"] = ptoRequest?.status
        ptoMap["selectedAdmins"] = selectedAdmins
        return ptoMap
    }

    override suspend fun approvePtoRequest(
        ptoRequestDomain: PtoRequestDomain
    ) {
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        val ptoListCollectionRef = FirebaseFirestore.getInstance().collection(PTO_LIST_COLLECTION)

        firebaseUser?.email?.let { email ->
            ptoListCollectionRef.whereArrayContains("approver", email).get()
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
}

fun LocalDate.toFirebaseTimestamp(): Timestamp {
    return Timestamp(
        Date.from(this.atStartOfDay(ZoneId.systemDefault()).toInstant())
    )
}