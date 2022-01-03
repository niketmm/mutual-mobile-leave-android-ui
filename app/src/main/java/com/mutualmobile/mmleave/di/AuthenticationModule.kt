package com.mutualmobile.mmleave.di

import com.mutualmobile.mmleave.data.service.AuthenticationService
import com.mutualmobile.mmleave.data.service.FirebaseAuthenticationService
import com.mutualmobile.mmleave.services.auth.GoogleSocialService
import com.mutualmobile.mmleave.services.auth.SocialService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AuthenticationModule {

  @Provides
  @Singleton
  fun provideAuthenticationService(): AuthenticationService {
    return FirebaseAuthenticationService()
  }
}

@InstallIn(ActivityComponent::class)
@Module
object GoogleAuthModule {

  @Provides
  @ActivityScoped
  fun provideGoogleAuthService(): SocialService<*, *> {
    return GoogleSocialService()
  }
}