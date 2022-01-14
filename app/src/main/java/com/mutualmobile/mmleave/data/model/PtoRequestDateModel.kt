package com.mutualmobile.mmleave.data.model

import android.graphics.Color

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