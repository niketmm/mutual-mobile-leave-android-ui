package com.mutualmobile.mmleave.services.auth

interface SocialService<in T, out U> {
  fun signIn(input:T): U
}