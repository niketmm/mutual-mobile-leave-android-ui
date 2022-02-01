package com.mutualmobile.mmleave.feature_auth.domain.usecase

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.mutualmobile.mmleave.feature_auth.data.repository.AuthenticationRepositoryImpl
import com.mutualmobile.mmleave.feature_auth.domain.repository.AuthenticationRepository

class GoogleAuthenticateUserUseCase(
   private val authenticateRepository: AuthenticationRepository
)  {

    suspend operator fun invoke(authCredential: AuthCredential) : AuthResult {
        return authenticateRepository.authenticateUser(authCredential = authCredential)
    }
}