package com.mutualmobile.mmleave.services.database.ptorequest

import com.google.firebase.Timestamp
import com.mutualmobile.mmleave.firestore.PtoStatus
import com.mutualmobile.mmleave.firestore.PtoStatus.PENDING

data class FirebasePtoRequest(
  var description: String?="" ,
  var assignedTo: String? ="",
  var currentStatus: PtoStatus?= PENDING,
  var date: Timestamp? = Timestamp.now()
)