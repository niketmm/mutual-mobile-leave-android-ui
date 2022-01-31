package com.mutualmobile.mmleave.feature_auth.data.data_source.auth.social

import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.mutualmobile.mmleave.feature_auth.data.data_source.auth.firebase.await
import javax.inject.Inject

class GoogleSocialService @Inject constructor() : SocialService<Intent, AuthCredential> {

  override suspend fun signIn(input: Intent): AuthCredential {
    val account = GoogleSignIn.getSignedInAccountFromIntent(input).await()
    return GoogleAuthProvider.getCredential(account.idToken!!, null)
  }

}