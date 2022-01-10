package com.mutualmobile.mmleave.firestore

import com.google.firebase.firestore.PropertyName
import java.time.LocalDate
import java.util.Date

data class PtoRequest(
  @PropertyName("pto_list")
  var ptoList: List<LocalDate>? = emptyList(),
  var email: String? = null,
  var description: String? = "",
  var assignedTo: String? = "",
)

fun Date.withinRange(
  startDate: Date,
  endDate: Date
): Boolean {
  return this.after(startDate) && this.before(endDate);
}

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