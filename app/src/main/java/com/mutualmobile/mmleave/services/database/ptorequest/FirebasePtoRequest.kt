package com.mutualmobile.mmleave.services.database.ptorequest

import com.google.firebase.Timestamp
import com.google.firebase.firestore.PropertyName

data class FirebasePtoRequest(
  var date: Timestamp?,
  var description: String? = "",
  var assignedTo: String? = ""
)