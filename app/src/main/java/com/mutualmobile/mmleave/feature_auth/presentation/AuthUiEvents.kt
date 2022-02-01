package com.mutualmobile.mmleave.feature_auth.presentation

sealed class AuthUiEvents {
    data class ShowFailureSnackBar(val message : String) : AuthUiEvents()
    object ProceedToHomeScreen : AuthUiEvents()
}
