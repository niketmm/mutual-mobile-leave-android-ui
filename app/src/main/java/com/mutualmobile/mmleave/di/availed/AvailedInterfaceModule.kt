package com.mutualmobile.mmleave.di.availed

import com.mutualmobile.mmleave.feature_availed.data.availed.AvailedPtoService
import com.mutualmobile.mmleave.feature_availed.data.availed.AvailedPtoServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@ExperimentalCoroutinesApi
abstract class AvailedInterfaceModule {

    @Binds
    @Singleton
    abstract fun provideAvailedServiceInterface(
        availedPtoServiceImpl: AvailedPtoServiceImpl
    ) : AvailedPtoService
}