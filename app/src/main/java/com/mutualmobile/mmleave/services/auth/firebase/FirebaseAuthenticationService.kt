package com.mutualmobile.mmleave.services.auth.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.mutualmobile.mmleave.exceptions.UnauthorizedException
import com.mutualmobile.mmleave.services.database.user.UserDataService
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class FirebaseAuthenticationService @Inject constructor(private val dataCollectionService: UserDataService<FirebaseUser>) :
  AuthenticationService {

  override suspend fun authenticateUser(authCredential: AuthCredential): AuthResult {
    if (authCredential is GoogleAuthCredential) {
      val authResult = FirebaseAuth.getInstance().signInWithCredential(authCredential).await()
      FirebaseAuth.getInstance().currentUser?.email?.let { safeEmail ->
        // user email
        if (isUserExistsInDatabase(safeEmail)) {
          dataCollectionService.updateUser(FirebaseAuth.getInstance().currentUser!!)
        } else {
          dataCollectionService.saveUser(FirebaseAuth.getInstance().currentUser!!)
        }
        return authResult
      } ?: run {
        throw UnauthorizedException()
      }
    } else {
      throw RuntimeException("We don't support anything apart of Google!")
    }
  }

  override suspend fun isUserExistsInDatabase(email: String): Boolean {
    return dataCollectionService.isUserExistInDB(email = email)
  }
}


suspend fun <T> Task<T>.await(): T {
  return suspendCoroutine { continuation ->
    this.addOnFailureListener { authException ->
      continuation.resumeWith(Result.failure(authException))
    }
    this.addOnSuccessListener { authResult ->
      continuation.resumeWith(Result.success(authResult))
    }
  }
}