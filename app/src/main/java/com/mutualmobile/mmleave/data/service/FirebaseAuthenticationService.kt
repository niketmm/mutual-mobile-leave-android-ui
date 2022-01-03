package com.mutualmobile.mmleave.data.service

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.mutualmobile.mmleave.exceptions.UnauthorizedException
import kotlinx.coroutines.suspendCancellableCoroutine

class FirebaseAuthenticationService(private val authCollectionService: UserAuthService<FirebaseUser>) :
  AuthenticationService {

  override suspend fun authenticateUser(authCredential: AuthCredential): AuthResult {
    if (authCredential is GoogleAuthCredential) {
      val authResult = FirebaseAuth.getInstance().signInWithCredential(authCredential).await()
      FirebaseAuth.getInstance().currentUser?.email?.let { safeEmail ->
        // user email
        if (isUserExistsInDatabase(safeEmail)) {
          authCollectionService.updateUser(FirebaseAuth.getInstance().currentUser!!)
        } else {
          authCollectionService.saveUser(FirebaseAuth.getInstance().currentUser!!)
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
    // fetch user details from user collections.
    return true
  }
}


suspend fun Task<AuthResult>.await(): AuthResult {
  return suspendCancellableCoroutine { continuation ->
    this.addOnFailureListener { authException ->
      continuation.resumeWith(Result.failure(authException))
    }
    this.addOnSuccessListener { authResult ->
      continuation.resumeWith(Result.success(authResult))
    }
    this.addOnCanceledListener {
      continuation.cancel()
    }
  }
}