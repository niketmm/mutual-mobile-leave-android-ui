package com.mutualmobile.mmleave.data.model

data class MMUser(
    var fullName : String,
    var leaveTaken : Int = 0,
    var totalLeave : Int = 20,
    var leaveLeft : Int = 20,
    var type : String = "employee",
    var emailId : String,
    var UUID : String,
    var nameAsArrays : ArrayList<String>
)