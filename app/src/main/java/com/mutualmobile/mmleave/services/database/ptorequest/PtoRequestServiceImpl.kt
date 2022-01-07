package com.mutualmobile.mmleave.services.database.ptorequest

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mutualmobile.mmleave.firestore.PtoRequest
import com.mutualmobile.mmleave.services.database.user.USERS_LIST_COLLECTION
import java.time.LocalDate

const val PTO_LIST_COLLECTION = "PTOs_list"

class PtoRequestServiceImpl : PtoRequestService {
  val TAG = PtoRequestServiceImpl::class.simpleName
  override suspend fun makePtoRequest(
    ptoRequest: PtoRequest
  ) {
    val pto = HashMap<String, Any?>()
    pto["description"] = ptoRequest.description
    pto["email"] = ptoRequest.email
    pto["pto_list"] = ptoRequest.ptoList

    FirebaseAuth.getInstance().currentUser?.email?.let { safeEmail ->
      FirebaseFirestore.getInstance()
          .collection(USERS_LIST_COLLECTION)
          .document(safeEmail)
          .collection(PTO_LIST_COLLECTION)
          .add(ptoRequest)
    }
  }

  override suspend fun approvePtoRequest(
    ptoRequest: PtoRequest
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