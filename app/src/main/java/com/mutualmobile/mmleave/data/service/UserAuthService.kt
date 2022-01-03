package com.mutualmobile.mmleave.data.service

interface UserAuthService<User> {
  fun findUserByEmail(email:String)
  fun saveUser(currentUser: User)
  fun updateUser(currentUser: User)
}
