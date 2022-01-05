package com.mutualmobile.mmleave.services.auth.social

import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.mutualmobile.mmleave.services.auth.firebase.await

class GoogleSocialService : SocialService<Intent, AuthCredential> {
  override suspend fun signIn(input: Intent): AuthCredential {
    val account = GoogleSignIn.getSignedInAccountFromIntent(input).await()
    return GoogleAuthProvider.getCredential(account.idToken!!, null)
  }
}