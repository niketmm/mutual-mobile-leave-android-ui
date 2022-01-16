package com.mutualmobile.mmleave.data.model

import com.google.firebase.Timestamp

data class FirebasePtoRequestModel(
    val date : Timestamp? = null,
    val description : String? = null,
    val email : String? = null,
    var ptoStatus : String? = null,
    var selectedAdmins : List<Admins?>? = emptyList()
)