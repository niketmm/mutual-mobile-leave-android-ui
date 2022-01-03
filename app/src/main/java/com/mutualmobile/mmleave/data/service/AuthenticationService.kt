package com.mutualmobile.mmleave.data.service

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult

interface AuthenticationService {
  suspend fun authenticateUser(authCredential: AuthCredential): AuthResult
  suspend fun isUserExistsInDatabase(email: String): Boolean
}