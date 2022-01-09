package com.mutualmobile.mmleave.data.model

data class MMUser(
    var displayName : String? = "",
    var designation : String? = "employee",
    var email : String? = "",
    var nameAsArrays : ArrayList<String>? = null,
    var photoUrl : String? = "",
    var userType : Int? = 0,
    var leaveTaken : Int? = 0,
    var totalLeave : Int? = 20,
    var leaveLeft : Int? = 20,
    var isSelected : Boolean? = true
)