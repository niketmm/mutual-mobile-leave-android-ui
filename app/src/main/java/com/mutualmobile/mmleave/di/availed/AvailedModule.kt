package com.mutualmobile.mmleave.di.availed

import com.mutualmobile.mmleave.services.database.availed.AvailedPtoService
import com.mutualmobile.mmleave.services.database.availed.AvailedPtoServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Module
@InstallIn(ActivityRetainedComponent::class)
@ExperimentalCoroutinesApi
object AvailedModule {

    @Provides
    fun provideAvailedServiceImpl() : AvailedPtoServiceImpl {
        return AvailedPtoServiceImpl()
    }
}