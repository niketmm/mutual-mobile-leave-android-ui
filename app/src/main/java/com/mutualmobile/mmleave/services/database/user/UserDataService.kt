package com.mutualmobile.mmleave.services.database.user

interface UserDataService<User> {
  suspend fun isUserExistInDB(email:String): Boolean
  suspend  fun saveUser(currentUser: User)
  suspend fun updateUser(currentUser: User)
}
