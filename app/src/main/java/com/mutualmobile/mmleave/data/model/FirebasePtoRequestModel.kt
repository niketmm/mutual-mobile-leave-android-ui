package com.mutualmobile.mmleave.data.model

import com.google.firebase.Timestamp

data class FirebasePtoRequestModel(
    val approvedBy : String? = null,
    val date : Timestamp? = null,
    val description : String? = null,
    val email : String? = null,
    var ptoStatus : String? = null,
    var selectedAdmins : List<String?>? = emptyList()
)