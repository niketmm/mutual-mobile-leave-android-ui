package com.mutualmobile.mmleave.services.database.ptorequest

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mutualmobile.mmleave.data.model.Admins
import com.mutualmobile.mmleave.data.model.MMUser
import com.mutualmobile.mmleave.di.FirebaseModule
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
        ptoRequests: List<SetGetPtoRequests?>,
        selectedAdmins : List<Admins?>
    ){
        ptoRequests.forEach {
            FirebaseModule.provideUserPtoRequestDocReference()
                .document(it?.date.toString())
                .set(getPtoMap(ptoRequest = it, selectedAdmins = selectedAdmins))
                .addOnSuccessListener {

                }
                .addOnFailureListener {

                }

        }
    }

    private fun getPtoMap(ptoRequest: SetGetPtoRequests?, selectedAdmins : List<Admins?>): HashMap<String, Any?> {
        val ptoMap = HashMap<String, Any?>()
        ptoMap["description"] = ptoRequest?.description
        ptoMap["email"] = ptoRequest?.email
        ptoMap["date"] = ptoRequest?.date?.toFirebaseTimestamp()
        ptoMap["ptoStatus"] = ptoRequest?.status
        ptoMap["selectedAdmins"] = selectedAdmins
        return ptoMap
    }

    override suspend fun approvePtoRequest(
        ptoRequests: SetGetPtoRequests
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