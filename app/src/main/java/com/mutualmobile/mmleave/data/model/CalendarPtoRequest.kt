package com.mutualmobile.mmleave.data.model

import com.google.firebase.Timestamp

data class CalendarPtoRequest(
    val date : Timestamp? = null,
    val description : String? = null,
    val email : String? = null,
    var ptoStatus : String? = null
)