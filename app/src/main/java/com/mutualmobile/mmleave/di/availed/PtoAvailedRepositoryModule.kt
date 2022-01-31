package com.mutualmobile.mmleave.di.availed

import com.mutualmobile.mmleave.feature_availed.data.repository.PtoAvailedRepositoryImpl
import com.mutualmobile.mmleave.feature_availed.domain.repository.PtoAvailedRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PtoAvailedRepositoryModule {

    @Binds
    @Singleton
    abstract fun provideAvailedRepository(
        availedPtoServiceImpl: PtoAvailedRepositoryImpl
    ) : PtoAvailedRepository
}