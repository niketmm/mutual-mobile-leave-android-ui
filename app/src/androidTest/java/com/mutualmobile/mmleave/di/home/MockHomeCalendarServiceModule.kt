package com.mutualmobile.mmleave.di.home

import com.mutualmobile.mmleave.feature_home.data.home.HomeCalendarDataService
import com.mutualmobile.mmleave.feature_home.data.home.HomeCalendarDataServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [HomeCalendarServiceModule::class]
)
@ExperimentalCoroutinesApi
abstract class MockHomeCalendarServiceModule {

    @Binds
    @Singleton
    abstract fun provideHomeCalendarDataService(
        homeCalendarDataServiceImpl: HomeCalendarDataServiceImpl
    ): HomeCalendarDataService
}