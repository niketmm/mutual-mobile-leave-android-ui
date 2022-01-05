package com.mutualmobile.mmleave.firestore

import java.util.Date

data class PtoProperties(
  var dateFrom: Date? = Date(),
  var dateTo: Date? = Date(),
  var email: String? = null,
  var description: String = "",
)

sealed class Designation {
  data class Employee(val department: String) : Designation()
  data class Staff(val department: String) : Designation()
  data class Admin(val department: String) : Designation()
}

sealed class Status {
  data class Approved(val message: String) : Status()
  data class NotDefined(val message: String) : Status()
  data class Rejected(val reason: String) : Status()
}