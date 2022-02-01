package com.mutualmobile.mmleave.di.apply_pto

import com.mutualmobile.mmleave.feature_pto.data.service.PtoRequestService
import com.mutualmobile.mmleave.feature_pto.data.service.PtoRequestServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@ExperimentalCoroutinesApi
abstract class PtoRequestServiceModule {

    @Binds
    @Singleton
    abstract fun providePtoRequestService(
        ptoRequestServiceImpl: PtoRequestServiceImpl
    ) : PtoRequestService
}