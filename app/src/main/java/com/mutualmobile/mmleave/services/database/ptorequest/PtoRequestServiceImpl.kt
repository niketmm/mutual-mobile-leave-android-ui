package com.mutualmobile.mmleave.services.database.ptorequest

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mutualmobile.mmleave.firestore.PtoProperties
import com.mutualmobile.mmleave.services.auth.firebase.await
import com.mutualmobile.mmleave.services.database.ptorequest.querypto.checkForExistingPtoRequest
import com.mutualmobile.mmleave.services.database.ptorequest.querypto.getAllPtoRequests

const val PTO_LIST_COLLECTION = "PTOs_list"

class PtoRequestServiceImpl : PtoRequestService {
  val TAG = PtoRequestServiceImpl::class.simpleName
  override suspend fun makePtoRequest(
    ptoProperties: PtoProperties
  ) {
    val pto = HashMap<String, Any?>()
    pto["dateFrom"] = ptoProperties.dateFrom
    pto["dateTo"] = ptoProperties.dateTo
    pto["description"] = ptoProperties.description
    pto["email"] = ptoProperties.email
    val map = getAllPtoRequests(ptoProperties)
    checkForExistingPtoRequest(ptoProperties, map)
    FirebaseFirestore.getInstance().collection(PTO_LIST_COLLECTION)
        .add(pto).await()
  }

  override suspend fun approvePtoRequest(
    ptoProperties: PtoProperties
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