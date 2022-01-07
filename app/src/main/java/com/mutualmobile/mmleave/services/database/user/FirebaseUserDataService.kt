package com.mutualmobile.mmleave.services.database.user

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.mutualmobile.mmleave.services.auth.firebase.await

const val USERS_LIST_COLLECTION = "users_list"
class FirebaseUserDataService : UserDataService<FirebaseUser> {
  override suspend fun isUserExistInDB(email: String): Boolean {
    val data = FirebaseFirestore.getInstance().collection(USERS_LIST_COLLECTION)
      .document(email).get().await()
    return data.exists()
  }

  override suspend fun saveUser(currentUser: FirebaseUser) {
    val user = HashMap<String, Any?>()
    user["email"] = currentUser.email
    user["displayName"] = currentUser.displayName
    user["photoUrl"] = currentUser.photoUrl.toString()
    user["userType"] = 1
    FirebaseFirestore.getInstance()
      .collection(USERS_LIST_COLLECTION)
      .document(currentUser.email!!)
      .set(user).await()
  }

  override suspend fun updateUser(currentUser: FirebaseUser) {
    val user = HashMap<String, Any?>()
    user["displayName"] = currentUser.displayName
    user["photoUrl"] = currentUser.photoUrl.toString()
    FirebaseFirestore.getInstance()
      .collection(USERS_LIST_COLLECTION)
      .document(currentUser.email!!)
      .update(user).await()
  }

}