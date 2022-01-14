package com.mutualmobile.mmleave.di

import com.google.firebase.auth.FirebaseUser
import com.mutualmobile.mmleave.services.auth.firebase.AuthenticationService
import com.mutualmobile.mmleave.services.auth.firebase.FirebaseAuthenticationService
import com.mutualmobile.mmleave.services.auth.social.GoogleSocialService
import com.mutualmobile.mmleave.services.database.ptorequest.PtoRequestServiceImpl
import com.mutualmobile.mmleave.services.database.user.FirebaseUserDataService
import com.mutualmobile.mmleave.services.database.user.UserDataService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.ExperimentalCoroutinesApi

@InstallIn(ViewModelComponent::class)
@Module
object AuthenticationModule {

  @Provides
  @ViewModelScoped
  fun provideAuthenticationService(userDataService: UserDataService<FirebaseUser>): AuthenticationService {
    return FirebaseAuthenticationService(userDataService)
  }
}

@InstallIn(ViewModelComponent::class)
@Module
object GoogleAuthModule {

  @Provides
  @ViewModelScoped
  fun provideGoogleAuthService(): GoogleSocialService {
    return GoogleSocialService()
  }
}

@InstallIn(ViewModelComponent::class)
@Module
object UserDataModule {
  @Provides
  @ViewModelScoped
  fun provideUserDataService(): UserDataService<FirebaseUser> {
    return FirebaseUserDataService()
  }
}

@ExperimentalCoroutinesApi
@InstallIn(ViewModelComponent::class)
@Module
object PtoModule {
    @Provides
    @ViewModelScoped
    fun providePtoRequestService(
    ): PtoRequestServiceImpl =
        PtoRequestServiceImpl()
}