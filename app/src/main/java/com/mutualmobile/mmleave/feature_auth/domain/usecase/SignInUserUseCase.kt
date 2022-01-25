package com.mutualmobile.mmleave.feature_auth.domain.usecase

import android.content.Intent
import com.google.firebase.auth.AuthCredential
import com.mutualmobile.mmleave.feature_auth.domain.repository.AuthenticationRepository

class SignInUserUseCase(
    private val authenticationRepository: AuthenticationRepository
) {

    suspend operator fun invoke(inputIntent : Intent) : AuthCredential {
        return authenticationRepository.signIn(input = inputIntent)
    }
}