package com.mutualmobile.mmleave.services.database.user

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.mutualmobile.mmleave.services.auth.firebase.await

class FirebaseUserDataService : UserDataService<FirebaseUser> {
  override suspend fun isUserExistInDB(email: String): Boolean {
    val data = FirebaseFirestore.getInstance().collection("users_list")
      .document(email).get().await()
    return data.exists()
  }

  override suspend fun saveUser(currentUser: FirebaseUser) {
    val user = HashMap<String, Any?>()
    user["email"] = currentUser.email
    user["displayName"] = currentUser.displayName
    user["photoUrl"] = currentUser.photoUrl
    user["userType"] = 1
    FirebaseFirestore.getInstance()
      .collection("users_list")
      .document(currentUser.email!!)
      .set(user).await()
  }

  override suspend fun updateUser(currentUser: FirebaseUser) {
    val user = HashMap<String, Any?>()
    user["displayName"] = currentUser.displayName
    user["photoUrl"] = currentUser.photoUrl
    FirebaseFirestore.getInstance()
      .collection("users_list")
      .document(currentUser.email!!)
      .update(user).await()
  }

}