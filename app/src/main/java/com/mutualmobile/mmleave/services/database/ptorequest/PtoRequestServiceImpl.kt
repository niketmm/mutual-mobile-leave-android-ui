package com.mutualmobile.mmleave.services.database.ptorequest

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mutualmobile.mmleave.firestore.PtoProperties
import com.mutualmobile.mmleave.services.auth.firebase.await

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
    FirebaseFirestore.getInstance().collection("PTOs_list").add(pto).await()
  }

  override suspend fun approvePtoRequest(
    ptoProperties: PtoProperties
  ) {
    val firebaseUser = FirebaseAuth.getInstance().currentUser
    val userListCollectionRef = FirebaseFirestore.getInstance().collection("PTOs_list")

    firebaseUser?.email?.let { email ->
      userListCollectionRef.whereArrayContains("approver", email).get()
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