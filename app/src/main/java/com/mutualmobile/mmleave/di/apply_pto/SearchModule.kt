package com.mutualmobile.mmleave.di.search

import com.mutualmobile.mmleave.feature_pto.data.service.FirebaseSearchUserServiceImpl
import com.mutualmobile.mmleave.feature_pto.data.service.SearchUserService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@ExperimentalCoroutinesApi
abstract class SearchUserModule {

    @Binds
    @Singleton
    abstract fun provideSearchService(
        firebaseSearchUserServiceImpl: FirebaseSearchUserServiceImpl
    ): SearchUserService
}
