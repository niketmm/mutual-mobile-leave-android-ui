package com.mutualmobile.mmleave.services.database.ptorequest.querypto

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mutualmobile.mmleave.firestore.PtoRequest
import com.mutualmobile.mmleave.firestore.withinRange
import com.mutualmobile.mmleave.services.auth.firebase.await
import com.mutualmobile.mmleave.services.database.ptorequest.FirebasePtoRequest
import com.mutualmobile.mmleave.services.database.ptorequest.PTO_LIST_COLLECTION
import com.mutualmobile.mmleave.services.database.ptorequest.PtoRequestServiceImpl
import com.mutualmobile.mmleave.services.database.user.USERS_LIST_COLLECTION
import java.sql.Timestamp

suspend fun PtoRequestServiceImpl.getAllPtoRequests(pto: PtoRequest): List<FirebasePtoRequest> {
  val firebaseUser = FirebaseAuth.getInstance().currentUser
  val userListCollectionRef = FirebaseFirestore.getInstance().collection(USERS_LIST_COLLECTION)
  val documents = firebaseUser?.email?.let {
    userListCollectionRef.document(it)
        .collection(PTO_LIST_COLLECTION)
        .whereLessThanOrEqualTo("dateFrom", pto.dateFrom!!)
        .get()
        .await()
  }
  return documents?.map {
    it.toObject(FirebasePtoRequest::class.java)
  } ?: listOf()
}

fun PtoRequestServiceImpl.filterDuplicatePtos(
  existingPtosList: List<FirebasePtoRequest>,
  ptoRequest: PtoRequest
): List<FirebasePtoRequest> {
  val duplicatePtoFound = mutableListOf<FirebasePtoRequest>()
  existingPtosList.forEach { pto ->
    if (pto.dateFrom.toDate().withinRange(ptoRequest.dateFrom!!, ptoRequest.dateTo!!) ||
        pto.dateTo.toDate().withinRange(ptoRequest.dateFrom!!, ptoRequest.dateTo!!)
    ) {
      duplicatePtoFound.add(pto)
    }
  }
  return duplicatePtoFound
}

