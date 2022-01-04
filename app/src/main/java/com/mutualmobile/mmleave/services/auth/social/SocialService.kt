package com.mutualmobile.mmleave.services.auth.social

interface SocialService<in T, out U> {
  suspend fun signIn(input:T): U?
}