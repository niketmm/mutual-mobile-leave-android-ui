package com.mutualmobile.mmleave.feature_auth.domain.usecase

import com.mutualmobile.mmleave.feature_auth.data.repository.AuthenticationRepositoryImpl
import com.mutualmobile.mmleave.feature_auth.domain.repository.AuthenticationRepository

class UserExistsUseCase(
    private val authenticationRepository: AuthenticationRepository
) {

    suspend operator fun invoke(userEmail : String) : Boolean{
        return authenticationRepository.isUserExistInDatabase(userEmail)
    }
}