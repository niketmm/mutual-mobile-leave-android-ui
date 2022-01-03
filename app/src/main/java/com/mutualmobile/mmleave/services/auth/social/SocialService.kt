package com.mutualmobile.mmleave.services.auth.social

interface SocialService<in T, out U> {
  fun signIn(input:T): U
}