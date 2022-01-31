package com.mutualmobile.mmleave.di.home

import com.mutualmobile.mmleave.feature_home.data.home.HomeCalendarDataService
import com.mutualmobile.mmleave.feature_home.data.home.HomeCalendarDataServiceImpl
import com.mutualmobile.mmleave.feature_home.domain.HomeRepository
import com.mutualmobile.mmleave.feature_home.domain.HomeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@ExperimentalCoroutinesApi
abstract class HomeCalendarServiceModule {

    @Binds
    @Singleton
    abstract fun provideHomeCalendarDataService(homeCalendarDataServiceImpl: HomeCalendarDataServiceImpl): HomeCalendarDataService
}