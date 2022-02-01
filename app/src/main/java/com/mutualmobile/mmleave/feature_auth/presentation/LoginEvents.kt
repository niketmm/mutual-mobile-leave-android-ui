package com.mutualmobile.mmleave.feature_auth.presentation

import android.content.Intent

sealed class LoginEvents{
    data class HitSignInButton(val intent: Intent) : LoginEvents()
}
