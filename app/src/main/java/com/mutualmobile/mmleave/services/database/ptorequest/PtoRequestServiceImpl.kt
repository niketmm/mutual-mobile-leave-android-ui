package com.mutualmobile.mmleave.services.database.ptorequest

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.mutualmobile.mmleave.di.FirebaseModule
import com.google.firebase.firestore.ktx.toObject
import com.mutualmobile.mmleave.data.model.SetGetPtoRequests
import com.mutualmobile.mmleave.services.auth.firebase.await
import kotlin.collections.HashMap
import com.mutualmobile.mmleave.util.Constants.PTO_LIST_COLLECTION
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.sql.Date
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

@ExperimentalCoroutinesApi
class PtoRequestServiceImpl @Inject constructor() : PtoRequestService {

    val TAG = PtoRequestServiceImpl::class.simpleName

    override suspend fun makePtoRequest(
        ptoProperties: List<SetGetPtoRequests?>
    ){
        ptoProperties.forEach {
            FirebaseModule.provideUserPtoRequestDocReference()
                .document(it?.date.toString())
                .set(getPtoMap(ptoRequest = it!!))
        }
    }

    private fun getPtoMap(ptoRequest: SetGetPtoRequests): HashMap<String, Any?> {
        val ptoMap = HashMap<String, Any?>()
        ptoMap["description"] = ptoRequest.description
        ptoMap["email"] = ptoRequest.email
        ptoMap["date"] = ptoRequest.date.toFirebaseTimestamp()
        ptoMap["ptoStatus"] = ptoRequest.status
        return ptoMap
    }

    override suspend fun approvePtoRequest(
        ptoProperties: SetGetPtoRequests
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