package com.mutualmobile.mmleave.di.home

import com.mutualmobile.mmleave.feature_home.domain.HomeRepository
import com.mutualmobile.mmleave.feature_home.domain.HomeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class HomeRepositoryModule {

    @Binds
    @Singleton
    abstract fun provideHomeRepository(homeRepositoryImpl: HomeRepositoryImpl) : HomeRepository
}