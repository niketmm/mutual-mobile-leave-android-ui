package com.mutualmobile.mmleave.feature_auth.data.repository

import android.content.Intent
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.mutualmobile.mmleave.feature_auth.domain.repository.AuthenticationRepository

class FakeAuthRepositoryImpl : AuthenticationRepository {

    private val fakeDatabaseList = listOf(
        "anmol.verma@mutaulmobile.com",
        "ambar.pandey@mutualmobile.com",
        "shubham.singh@mutualmobile.com",
        "niket.jain@mutualmobile.com"
    )

    override suspend fun signIn(input: Intent): AuthCredential {
        TODO("Not yet implemented")
    }

    override suspend fun authenticateUser(authCredential: AuthCredential): AuthResult {
        TODO("Not yet implemented")
    }

    override suspend fun isUserExistInDatabase(emailId: String): Boolean {
        return fakeDatabaseList.contains(emailId)
    }

}