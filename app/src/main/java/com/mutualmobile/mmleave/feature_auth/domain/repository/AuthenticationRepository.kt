package com.mutualmobile.mmleave.feature_auth.domain.repository

import android.content.Intent
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult

interface AuthenticationRepository {
    suspend fun signIn(input : Intent) : AuthCredential
    suspend fun authenticateUser(authCredential: AuthCredential) : AuthResult
    suspend fun isUserExistInDatabase(emailId : String) : Boolean
}