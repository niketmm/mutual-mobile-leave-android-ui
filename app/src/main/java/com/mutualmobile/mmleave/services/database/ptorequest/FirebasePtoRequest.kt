package com.mutualmobile.mmleave.services.database.ptorequest

import com.google.firebase.Timestamp
import com.google.firebase.firestore.PropertyName

data class FirebasePtoRequest(
  @PropertyName("pto_list")
  var pto_list: List<Timestamp>? = null,
  var description: String? = "",
  var email: String? = "",
)