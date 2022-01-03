package com.mutualmobile.mmleave.firestore

import com.mutualmobile.mmleave.model.User
import java.util.Date

data class PtoProperties(
  var email: String,
  var userId: String,
  var dateFrom: Date,
  var dateTo: Date,
  var description: String="",
  var designation: Designation,
  var status: Status,
  var approver: List<User>
)

sealed class Designation {
  data class Employee(val department: String) : Designation()
  data class Staff(val department: String) : Designation()
  data class Admin(val department: String) : Designation()
}

sealed class Status {
  data class Approved(val message: String): Status()
  data class Rejected(val reason: String) : Status()
}