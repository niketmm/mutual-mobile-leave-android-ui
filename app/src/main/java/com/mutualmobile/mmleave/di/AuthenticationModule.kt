package com.mutualmobile.mmleave.di

import android.content.Intent
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import com.mutualmobile.mmleave.services.auth.firebase.AuthenticationService
import com.mutualmobile.mmleave.services.auth.firebase.FirebaseAuthenticationService
import com.mutualmobile.mmleave.services.auth.social.GoogleSocialService
import com.mutualmobile.mmleave.services.auth.social.SocialService
import com.mutualmobile.mmleave.services.database.user.FirebaseUserDataService
import com.mutualmobile.mmleave.services.database.user.UserDataService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AuthenticationModule {

  @Provides
  @Singleton
  fun provideAuthenticationService(userDataService: UserDataService<FirebaseUser>): AuthenticationService {
    return FirebaseAuthenticationService(userDataService)
  }
}

@InstallIn(ViewModelComponent::class)
@Module
object GoogleAuthModule {

  @Provides
  @ViewModelScoped
  fun provideGoogleAuthService(): SocialService<Intent, AuthCredential> {
    return GoogleSocialService()
  }
}

@InstallIn(SingletonComponent::class)
@Module
object UserDataModule {
  @Provides
  @Singleton
  fun provideUserDataService(): UserDataService<FirebaseUser> {
    return FirebaseUserDataService()
  }
}