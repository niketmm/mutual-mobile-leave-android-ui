package com.mutualmobile.mmleave.di.authentication

import com.google.firebase.auth.FirebaseUser
import com.mutualmobile.mmleave.feature_auth.data.data_source.auth.firebase.AuthenticationService
import com.mutualmobile.mmleave.feature_auth.data.data_source.auth.firebase.FirebaseAuthenticationService
import com.mutualmobile.mmleave.feature_auth.data.data_source.auth.social.GoogleSocialService
import com.mutualmobile.mmleave.services.database.ptorequest.PtoRequestServiceImpl
import com.mutualmobile.mmleave.feature_auth.data.data_source.user.FirebaseUserDataService
import com.mutualmobile.mmleave.feature_auth.data.data_source.user.UserDataService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

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

@InstallIn(SingletonComponent::class)
@Module
abstract class UserDataModule {

    @Binds
    @Singleton
    abstract fun provideUserDataService(firebaseUserDataService: FirebaseUserDataService): UserDataService<FirebaseUser>
}

// Todo : Put it to the Different module
@ExperimentalCoroutinesApi
@InstallIn(ViewModelComponent::class)
@Module
object PtoModule {
    @Provides
    @ViewModelScoped
    fun providePtoRequestService() : PtoRequestServiceImpl =
        PtoRequestServiceImpl()
}