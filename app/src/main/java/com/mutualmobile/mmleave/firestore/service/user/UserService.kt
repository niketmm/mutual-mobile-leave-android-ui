package com.mutualmobile.mmleave.firestore.service.user

import com.mutualmobile.mmleave.model.User

interface UserService {
  fun addUser(userItem: User)
  fun isUserAlreadyAdded(email: String): Boolean
}