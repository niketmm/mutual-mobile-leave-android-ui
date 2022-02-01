package com.mutualmobile.mmleave.feature_auth.data.data_source.auth.firebase

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult

interface AuthenticationService {
  suspend fun authenticateUser(authCredential: AuthCredential): AuthResult
  suspend fun isUserExistsInDatabase(email: String): Boolean
}