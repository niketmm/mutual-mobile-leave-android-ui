package com.mutualmobile.mmleave.feature_auth.data.data_source.user

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.mutualmobile.mmleave.di.FirebaseModule
import com.mutualmobile.mmleave.feature_auth.data.data_source.auth.firebase.await
import javax.inject.Inject

class FirebaseUserDataService @Inject constructor() : UserDataService<FirebaseUser> {
    override suspend fun isUserExistInDB(email: String): Boolean {
        val data = FirebaseFirestore.getInstance().collection("users_list")
            .document(email).get().await()
        return data.exists()
    }

  override suspend fun saveUser(currentUser: FirebaseUser) {
    val user = HashMap<String, Any?>()
    user["email"] = currentUser.email
    user["displayName"] = currentUser.displayName
    user["photoUrl"] = currentUser.photoUrl.toString()
    user["userType"] = 1
    user["nameAsArray"] = generateNameAsArray(currentUser.displayName!!)
    user["totalLeave"] = 24
    user["leaveLeft"] = 24

    FirebaseModule.provideFirebaseUserCollectionReference()
      .document(currentUser.email!!)
      .set(user)
      .await()
  }

  /**
   * If display name changes the NameArray will also get updated
   */
  override suspend fun updateUser(currentUser: FirebaseUser) {
    val user = HashMap<String, Any?>()
    user["displayName"] = currentUser.displayName
    user["photoUrl"] = currentUser.photoUrl.toString()
    user["nameAsArray"] = generateNameAsArray(currentUser.displayName!!)

    FirebaseModule.provideFirebaseUserCollectionReference()
      .document(currentUser.email!!)
      .update(user).await()
  }

    private fun generateNameAsArray(displayName: String): List<String> {
        val keywords = mutableListOf<String>()
        for (i in displayName.indices) {
            for (j in (i + 1)..displayName.length) {
                keywords.add(displayName.slice(i until j).lowercase())
            }
        }
        return keywords
    }
}