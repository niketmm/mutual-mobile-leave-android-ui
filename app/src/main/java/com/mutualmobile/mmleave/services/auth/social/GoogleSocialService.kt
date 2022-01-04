package com.mutualmobile.mmleave.services.auth.social

import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.mutualmobile.mmleave.services.auth.firebase.await
import com.mutualmobile.mmleave.util.MUTUAL_MOBILE_DOMAIN

class GoogleSocialService : SocialService<Intent, AuthCredential> {

  override suspend fun signIn(input: Intent): AuthCredential? {
    val account = GoogleSignIn.getSignedInAccountFromIntent(input).await()
    if (verifyEmail(account.email)){
      return GoogleAuthProvider.getCredential(account.idToken!!, null)
    }
    return null
  }

  private fun verifyEmail(email : String?): Boolean {
    val domain = email?.substringAfterLast("@")
    return domain == MUTUAL_MOBILE_DOMAIN
  }
}