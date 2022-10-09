package com.mutualmobile.mmleave.di.home

import com.mutualmobile.mmleave.feature_home.data.home.SyncCacheHomeDataService
import com.mutualmobile.mmleave.feature_home.data.home.SyncCacheHomeDataServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [SyncCacheHomeDataServiceModule::class]
)
abstract class MockSyncCacheHomeDataServiceModule {

    @Binds
    @Singleton
    abstract fun provideSyncCacheDataService(
        syncCacheHomeDataServiceImpl: SyncCacheHomeDataServiceImpl
    ): SyncCacheHomeDataService
}