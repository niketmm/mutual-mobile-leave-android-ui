package com.mutualmobile.mmleave.firestore.service

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.mutualmobile.mmleave.firestore.PtoProperties
import com.mutualmobile.mmleave.model.User

class PtoRequestServiceImpl(private val fireStore: FirebaseFirestore) : PtoRequestService {
  val TAG = PtoRequestServiceImpl::class.simpleName
  override fun makePtoRequest(
    user: User,
    ptoProperties: PtoProperties
  ) {
    val pto = HashMap<String, Any>()
    pto["name"] = ptoProperties.email
    pto["userId"] = ptoProperties.userId
    pto["dateTo"] = ptoProperties.dateTo
    pto["description"] = ptoProperties.description
    pto["designation"] = ptoProperties.designation
    pto["status"] = ptoProperties.status
    pto["approver"] = ptoProperties.approver
    fireStore.collection("PTOs_list").document(user.email)
        .set(pto)
        .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
        .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
  }

  override fun approvePtoRequest(
    ptoProperties: PtoProperties,
    user: User
  ) {
    val userListCollectionRef = fireStore.collection("PTOs_list")

    userListCollectionRef.whereArrayContains("approver", user.email).get()
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