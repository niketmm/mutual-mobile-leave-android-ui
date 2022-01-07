package com.mutualmobile.mmleave.services.database.ptorequest

import com.google.firebase.Timestamp
import com.google.firebase.firestore.PropertyName

data class FirebasePtoRequest(
  @PropertyName("pto_list")
  var ptoList: List<Timestamp> = emptyList(),
  var description: String = "",
  var email: String = "",
)