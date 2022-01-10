package com.mutualmobile.mmleave.model

import com.mutualmobile.mmleave.firestore.Designation

data class User(
  val email: String,
  val UUID: String,
  val name: String,
  val designation: Designation,
  val userType: String,
  val imageUrl : String?
)