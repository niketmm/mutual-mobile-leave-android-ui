package com.mutualmobile.mmleave.model

data class PtoRequest(
  val name: String,
  val leavesLeft: Int,
  val date: String,
  val description: String,
  val approvedBy: String,
  val status: String
)
