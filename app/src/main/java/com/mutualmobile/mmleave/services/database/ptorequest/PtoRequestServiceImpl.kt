package com.mutualmobile.mmleave.services.database.ptorequest

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.mutualmobile.mmleave.di.FirebaseModule
import com.google.firebase.firestore.ktx.toObject
import com.mutualmobile.mmleave.data.model.MMUser
import com.mutualmobile.mmleave.di.FirebaseModule
import com.mutualmobile.mmleave.firestore.PtoRequest
import com.mutualmobile.mmleave.firestore.PtoRequestDateModel
import com.mutualmobile.mmleave.firestore.SetGetPtoRequests
import com.mutualmobile.mmleave.services.auth.firebase.await
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.time.LocalDate
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap
import com.mutualmobile.mmleave.util.Constants.PTO_LIST_COLLECTION
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.onFailure
import kotlinx.coroutines.flow.callbackFlow
import java.sql.Date
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

@ExperimentalCoroutinesApi
class PtoRequestServiceImpl @Inject constructor() : PtoRequestService {

    val TAG = PtoRequestServiceImpl::class.simpleName

    override suspend fun makePtoRequest(
        ptoRequest: List<SetGetPtoRequests?>
    ): Boolean {
        ptoRequest.forEach {
            FirebaseModule.provideUserPtoRequestDocReference()
                .document(it?.date.toString())
                .set(getPtoMap(ptoRequest = it!!))
        }
        return true
    }

    private fun getPtoMap(ptoRequest: SetGetPtoRequests): HashMap<String, Any?> {
        val ptoMap = HashMap<String, Any?>()
        ptoMap["description"] = ptoRequest.description
        ptoMap["email"] = ptoRequest.email
        ptoMap["date"] = ptoRequest.date.toFirebaseTimestamp()
        ptoMap["ptoStatus"] = ptoRequest.status
        return ptoMap
    }

    private suspend fun filterPtoRequests(newPtoList: List<Timestamp>): List<FirebasePtoRequest> {
        val duplicatePtoRequestList = mutableListOf<FirebasePtoRequest>()
        FirebaseAuth.getInstance().currentUser?.email?.let { safeEmail ->
            val userPtoListCollectionRef = getAllPtoRequestsCollection(safeEmail)
            val documents =
                userPtoListCollectionRef.whereArrayContainsAny("pto_list", newPtoList).get()
                    .await()
                    .documents
            documents.forEach { document ->
                document.toObject<FirebasePtoRequest>()?.let {
                    duplicatePtoRequestList.add(it)
                    it.pto_list?.size
                }
            }
        }
        return duplicatePtoRequestList
    }

    suspend fun getAllPtoRequestsList(): List<FirebasePtoRequest> {
        val allPtosList = mutableListOf<FirebasePtoRequest>()
        FirebaseAuth.getInstance().currentUser?.email?.let { safeEmail ->
            val userPtoListCollectionRef = getAllPtoRequestsCollection(safeEmail)
            val documents = userPtoListCollectionRef.get()
                .await()
                .documents
            documents.forEach { document ->
                document.toObject<FirebasePtoRequest>()?.let {
                    allPtosList.add(it)
                    it.pto_list?.size
                }
            }
        }
        return allPtosList
    }

    private fun getAllPtoRequestsCollection(safeEmail: String): CollectionReference {
        return FirebaseModule.provideFirebaseUserCollectionReference()
            .document(safeEmail)
            .collection(PTO_LIST_COLLECTION)
    }

    override suspend fun approvePtoRequest(
        ptoRequest: SetGetPtoRequests
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

//fun makePtoRequestOld(ptoRequest: PtoRequest) {
//    val duplicatePtoList =
//        ptoRequest.ptoList
//    ptoRequest.ptoList?.toFirebaseTimestamp()?.let { filterPtoRequests(it) }
//    if (duplicatePtoList?.isEmpty() == true) {
//        ptoRequest.ptoList?.forEach {
//            firebasePtoRequestCollectionReference
//                .document(it.toString())
//                .set(getPtoMap(ptoRequest = ptoRequest))
//        }
//    } else {
//        // Updating if duplicate Exists
//        ptoRequest.ptoList?.forEach {
//            firebasePtoRequestCollectionReference
//                .document(it.toString())
//                .update(getPtoMap(ptoRequest = ptoRequest))
//        }
//    }
//}