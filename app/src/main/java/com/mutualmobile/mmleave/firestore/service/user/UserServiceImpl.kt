package com.mutualmobile.mmleave.firestore.service.user

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.mutualmobile.mmleave.model.User

class UserServiceImpl(private val fireStore: FirebaseFirestore) : UserService {
  val TAG = " UserServiceImpl"
  override fun addUser(userItem: User) {
    val user = HashMap<String, Any>()
    user["email"] = userItem.email
    user["UUID"] = userItem.UUID
    user["designation"] = userItem.designation
    user["userType"] = userItem.userType
    fireStore.collection("users_list").document(userItem.email)
        .set(user)
        .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
        .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
  }

  override fun isUserAlreadyAdded(email: String): Boolean {
    val userListCollectionRef = fireStore.collection("users_list")

    userListCollectionRef.whereArrayContains("email", email).get()
        .addOnSuccessListener { documents ->
          for (document in documents) {
            Log.d(TAG, "${document.id} => ${document.data}")
          }
        }
        .addOnFailureListener { exception ->
          Log.w(TAG, "Error getting documents: ", exception)
        }
    return true
  }
}