package com.mutualmobile.mmleave.di.home

import com.mutualmobile.mmleave.feature_home.data.FakeHomeRepository
import com.mutualmobile.mmleave.feature_home.data.home.HomeRepositoryImpl
import com.mutualmobile.mmleave.feature_home.domain.repo.HomeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [HomeRepositoryModule::class]
)
abstract class MockHomeRepositoryModule {

    @Binds
    @Singleton
    abstract fun provideHomeRepository(
        fakeHomeRepository: FakeHomeRepository
    ) : HomeRepository
}

