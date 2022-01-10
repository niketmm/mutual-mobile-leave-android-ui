package com.mutualmobile.mmleave.services.database.ptorequest

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.mutualmobile.mmleave.firestore.PtoRequest
import com.mutualmobile.mmleave.services.auth.firebase.await
import com.mutualmobile.mmleave.services.database.user.USERS_LIST_COLLECTION
import java.sql.Date
import java.time.LocalDate
import java.time.ZoneId

const val PTO_LIST_COLLECTION = "pto_requests"

class PtoRequestServiceImpl : PtoRequestService {
  val TAG = PtoRequestServiceImpl::class.simpleName
  override suspend fun makePtoRequest(
    ptoRequest: PtoRequest
  ): Boolean {
    val duplicatePtoList = ptoRequest.ptoList?.toFirebaseTimestamp()?.let { filterPtoRequests(it) }
    if (duplicatePtoList?.isEmpty() == true) {
      FirebaseAuth.getInstance().currentUser?.email?.let { safeEmail ->
        getAllPtoRequestsCollection(safeEmail)
            .add(getPtoMap(ptoRequest))
      }
    } else {
      Log.d(TAG, "makePtoRequest:existing pto request found ")
      return false
    }
    return true
  }

  private fun getPtoMap(ptoRequest: PtoRequest): HashMap<String, Any?> {
    val pto = HashMap<String, Any?>()
    pto["description"] = ptoRequest.description
    pto["email"] = ptoRequest.email
    pto["pto_list"] = ptoRequest.ptoList?.toFirebaseTimestamp()
    return pto
  }

  private suspend fun filterPtoRequests(newPtoList: List<Timestamp>): List<FirebasePtoRequest> {
    val duplicatePtoRequestList = mutableListOf<FirebasePtoRequest>()
    FirebaseAuth.getInstance().currentUser?.email?.let { safeEmail ->
      val userPtoListCollectionRef = getAllPtoRequestsCollection(safeEmail)
      val documents = userPtoListCollectionRef.whereArrayContainsAny("pto_list", newPtoList).get()
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
    return FirebaseFirestore.getInstance()
        .collection(USERS_LIST_COLLECTION)
        .document(safeEmail)
        .collection(PTO_LIST_COLLECTION)
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

fun List<LocalDate>.toFirebaseTimestamp(): List<Timestamp> {
  return this.map { localDate ->
    Timestamp(
        Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
    )
  }
}