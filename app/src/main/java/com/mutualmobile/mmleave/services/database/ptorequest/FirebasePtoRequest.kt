package com.mutualmobile.mmleave.services.database.ptorequest

import com.google.firebase.Timestamp

data class FirebasePtoRequest(
  var dateFrom: Timestamp = Timestamp.now(),
  var dateTo: Timestamp = Timestamp.now(),
  var description: String="",
  var email: String="",
)