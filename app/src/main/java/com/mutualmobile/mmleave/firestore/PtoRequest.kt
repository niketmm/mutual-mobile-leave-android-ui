package com.mutualmobile.mmleave.firestore

import com.google.firebase.firestore.PropertyName
import com.mutualmobile.mmleave.firestore.PtoStatus.PENDING
import java.time.LocalDate
import java.util.Date

data class PtoRequest(
  @PropertyName("pto_list")
  var ptoList: List<LocalDate>? = emptyList(),
  var email: String? = null,
  var description: String? = "",
  var assignedTo: String? = "",
  var currentStatus: PtoStatus = PENDING
)

enum class PtoStatus {
  APPROVED,
  REJECTED,
  PENDING
}

sealed class Designation {
  data class Employee(val department: String) : Designation()
  data class Staff(val department: String) : Designation()
  data class Admin(val department: String) : Designation()
}
