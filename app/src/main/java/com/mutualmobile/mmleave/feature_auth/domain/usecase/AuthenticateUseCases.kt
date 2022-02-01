package com.mutualmobile.mmleave.feature_auth.domain.usecase

data class AuthenticateUseCases(
    val signInUserUseCase: SignInUserUseCase,
    val userExistsUseCase: UserExistsUseCase,
    val googleAuthenticateUserUseCase: GoogleAuthenticateUserUseCase
)