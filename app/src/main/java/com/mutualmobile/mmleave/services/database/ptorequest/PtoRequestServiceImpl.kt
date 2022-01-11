package com.mutualmobile.mmleave.services.database.ptorequest

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
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
    val duplicatePtoList = ptoRequest.ptoList?.let {
      val documentsList = getPtoRequestDocuments(it)
      filterPtoRequests(documentsList)
    }
    val currentUserEmail = getCurrentUserEmail()
    val ptoRequestCollection = currentUserEmail?.let { getAllPtoRequestsCollection(it) }

    if (duplicatePtoList?.isEmpty() == true) {
      currentUserEmail?.let {
        ptoRequest.ptoList!!.forEach { pto ->
          ptoRequestCollection?.document(pto.toString())
              ?.set(getPtoMap(ptoRequest,pto.toFirebaseTimestamp()))
        }
      }
    } else {
      currentUserEmail?.let {
        duplicatePtoList?.forEach { pto ->
          ptoRequestCollection?.document(pto.toString())
              ?.update(getPtoMap(ptoRequest, pto.date))
        }
        Log.d(TAG, "makePtoRequest:existing pto   request found ")
        return false
      }
    }
    return true
  }

  private fun getPtoMap(
    ptoRequest: PtoRequest,
    timestamp: Timestamp?
  ): HashMap<String, Any?> {
    val pto = HashMap<String, Any?>()
    pto["description"] = ptoRequest.description
    pto["assignedTo"] = ptoRequest.assignedTo
    pto["currentStatus"] = ptoRequest.currentStatus
    pto["date"]= timestamp
    // pto["email"] = ptoRequest.email
    // pto["pto_list"] = ptoRequest.ptoList?.toFirebaseTimestamp()
    return pto
  }

  private suspend fun getPtoRequestDocuments(newPtoList: List<LocalDate>): List<DocumentSnapshot> {
    val documents = mutableListOf<DocumentSnapshot>()
    getCurrentUserEmail()?.let { safeEmail ->
      val userPtoListCollectionRef = getAllPtoRequestsCollection(safeEmail)
      newPtoList.forEach {
        documents.add(
            userPtoListCollectionRef.document(it.toString())
                .get()
                .await()
        )
      }
    }
    return documents
  }

  private fun filterPtoRequests(documents: List<DocumentSnapshot>): List<FirebasePtoRequest> {
    val duplicatePtoRequestList = mutableListOf<FirebasePtoRequest>()
    documents.forEach { document ->
      Log.d(TAG, "filterPtoRequests: " + document.id + " data")
      document.toObject<FirebasePtoRequest>()?.let {
        Log.d(TAG, "filterPtoRequests: " + it)
        duplicatePtoRequestList.add(it)
      }
    }
    return duplicatePtoRequestList
  }

  suspend fun getAllPtoRequestsList(): List<FirebasePtoRequest> {
    val allPtosList = mutableListOf<FirebasePtoRequest>()
    getCurrentUserEmail()?.let { safeEmail ->
      val userPtoListCollectionRef = getAllPtoRequestsCollection(safeEmail)
      val documents = userPtoListCollectionRef.get()
          .await()
          .documents
      documents.forEach { document ->
        document.toObject<FirebasePtoRequest>()?.let {
          allPtosList.add(it)
        }
      }
    }
    return allPtosList
  }

  private fun getCurrentUserEmail() = FirebaseAuth.getInstance().currentUser?.email

  private fun getAllPtoRequestsCollection(safeEmail: String): CollectionReference {
    return FirebaseFirestore.getInstance()
        .collection(USERS_LIST_COLLECTION)
        .document(safeEmail)
        .collection(PTO_LIST_COLLECTION)
  }

  override suspend fun approvePtoRequest(
    ptoRequest: PtoRequest
  ) {
    val ptoListCollectionRef = FirebaseFirestore.getInstance().collection(PTO_LIST_COLLECTION)

    getCurrentUserEmail()?.let { email ->
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
