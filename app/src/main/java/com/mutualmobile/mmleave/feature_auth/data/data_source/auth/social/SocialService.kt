package com.mutualmobile.mmleave.feature_auth.data.data_source.auth.social

interface SocialService<in T, out U> {
  suspend fun signIn(input:T): U
}