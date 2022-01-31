package com.mutualmobile.mmleave.di.home

import com.mutualmobile.mmleave.feature_home.data.home.SyncCacheHomeDataService
import com.mutualmobile.mmleave.feature_home.data.home.SyncCacheHomeDataServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SyncCacheHomeDataServiceModule {

    @Binds
    @Singleton
    abstract fun provideSyncCacheDataService(
        syncCacheHomeDataServiceImpl: SyncCacheHomeDataServiceImpl
    ): SyncCacheHomeDataService
}