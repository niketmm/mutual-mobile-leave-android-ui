package com.mutualmobile.mmleave.di

import com.mutualmobile.mmleave.services.database.ptorequest.PtoRequestService
import com.mutualmobile.mmleave.services.database.ptorequest.PtoRequestServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class PtoRequestServiceModule {

    @Binds
    abstract fun bindPtoRequestServiceImpl(
        ptoRequestServiceImpl: PtoRequestServiceImpl
    ) : PtoRequestService
}