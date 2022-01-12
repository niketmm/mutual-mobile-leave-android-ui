package com.mutualmobile.mmleave.firestore

import android.graphics.Color
import com.google.firebase.firestore.PropertyName
import java.time.LocalDate
import java.util.Date

data class PtoRequest(
  @PropertyName("pto_list")
  var ptoList: List<LocalDate>? = emptyList(),
  var email: String? = null,
  var description: String? = "",
)

data class SetGetPtoRequests(
  var email: String? = null,
  var description: String? = "",
  var date : LocalDate?,
  var status: PtoRequestDateModel.PtoGraphStatus = PtoRequestDateModel.PtoGraphStatus.APPLIED
)

data class PtoRequestDateModel(
  var status : PtoGraphStatus = PtoGraphStatus.APPLIED
){
  companion object{
    val pending = PtoGraphStatus.PENDING
    val applied = PtoGraphStatus.APPLIED
    val approved = PtoGraphStatus.APPROVED
    val updated = PtoGraphStatus.UPDATED
    val holiday = PtoGraphStatus.HOLIDAY
    fun rejected(reason : String?) = PtoGraphStatus.REJECTED
  }

  enum class PtoGraphStatus(val color: Int){
    APPLIED(Color.GREEN),
    APPROVED(Color.CYAN),
    HOLIDAY(Color.YELLOW),
    REJECTED(Color.RED),
    PENDING(Color.BLUE),
    UPDATED(Color.MAGENTA)
  }
}



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