package com.mutualmobile.mmleave.feature_auth.data.data_source.user

interface UserDataService<User> {
  suspend fun isUserExistInDB(email:String): Boolean
  suspend  fun saveUser(currentUser: User)
  suspend fun updateUser(currentUser: User)
}
