package com.mutualmobile.mmleave.di.search

import com.mutualmobile.mmleave.services.database.search_user.FirebaseSearchUserServiceImpl
import com.mutualmobile.mmleave.services.database.search_user.SearchUserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@InstallIn(ViewModelComponent::class)
@Module
object SearchUserModule {
    @Provides
    @ViewModelScoped
    fun provideSearchService(): SearchUserService = FirebaseSearchUserServiceImpl()
}
