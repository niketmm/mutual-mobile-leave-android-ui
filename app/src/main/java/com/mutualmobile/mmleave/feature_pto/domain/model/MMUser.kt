package com.mutualmobile.mmleave.feature_pto.domain.model

data class MMUser(
    var displayName : String? = "",
    var designation : String? = "employee",
    var email : String? = "",
    var nameAsArrays : ArrayList<String>? = null,
    var photoUrl : String? = "",
    var userType : Int? = 0,
    var totalLeave : Int? = 20,
    var leaveLeft : Int? = 20,
    var isSelected : Boolean? = false
)