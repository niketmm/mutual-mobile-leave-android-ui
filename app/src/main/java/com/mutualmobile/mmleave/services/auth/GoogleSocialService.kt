package com.mutualmobile.mmleave.services.auth

import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider

class GoogleSocialService : SocialService<Intent, AuthCredential> {
  override fun signIn(input: Intent): AuthCredential {
    val task = GoogleSignIn.getSignedInAccountFromIntent(input)
    val account = task.getResult(ApiException::class.java)!!
    return GoogleAuthProvider.getCredential(account.idToken!!, null)
  }
}