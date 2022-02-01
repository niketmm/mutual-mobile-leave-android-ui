package com.mutualmobile.mmleave.feature_pto.domain.model

import com.google.firebase.Timestamp

data class FirebasePtoRequestModel(
    val approvedBy : String? = null,
    val rejectedBy : String? = null,
    val date : Timestamp? = null,
    val description : String? = null,
    val email : String? = null,
    var ptoStatus : String? = null,
    var selectedAdmins : List<String?>? = emptyList()
)