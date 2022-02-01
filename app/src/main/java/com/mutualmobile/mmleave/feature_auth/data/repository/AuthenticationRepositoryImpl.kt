package com.mutualmobile.mmleave.feature_auth.data.repository

import android.content.Intent
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.mutualmobile.mmleave.feature_auth.domain.repository.AuthenticationRepository
import com.mutualmobile.mmleave.feature_auth.data.data_source.auth.firebase.AuthenticationService
import com.mutualmobile.mmleave.feature_auth.data.data_source.auth.social.GoogleSocialService
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val socialService: GoogleSocialService,
    private val authenticationService: AuthenticationService
) : AuthenticationRepository {

    override suspend fun signIn(input : Intent) : AuthCredential {
       return socialService.signIn(input = input)
    }

    override suspend fun authenticateUser(authCredential: AuthCredential) : AuthResult {
       return authenticationService.authenticateUser(authCredential = authCredential)
    }

    override suspend fun isUserExistInDatabase(emailId : String) : Boolean {
       return authenticationService.isUserExistsInDatabase(email = emailId)
    }
}