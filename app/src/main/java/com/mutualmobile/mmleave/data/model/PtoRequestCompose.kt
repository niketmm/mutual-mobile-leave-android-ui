package com.mutualmobile.mmleave.data.model

data class PtoRequestCompose(
  val name: String,
  val leavesLeft: Int,
  val date: String,
  val description: String,
  val approvedBy: String,
  val status: String
)
