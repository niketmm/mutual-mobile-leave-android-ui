package com.mutualmobile.mmleave.di.availed

import com.mutualmobile.mmleave.data.data_store.StoreUserInfo
import com.mutualmobile.mmleave.feature_availed.data.availed.AvailedPtoService
import com.mutualmobile.mmleave.feature_availed.data.availed.AvailedPtoServiceImpl
import com.mutualmobile.mmleave.feature_availed.data.repository.PtoAvailedRepositoryImpl
import dagger.Module
import dagger.Provides
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
object AvailedModule {

    @Provides
    @Singleton
    fun provideAvailedServiceImpl() : AvailedPtoServiceImpl {
        return AvailedPtoServiceImpl()
    }

    @Provides
    @Singleton
    fun providePtoAvailedRepository(
        availedPtoService: AvailedPtoService,
        storeUserInfo: StoreUserInfo
    ) : PtoAvailedRepositoryImpl {
        return PtoAvailedRepositoryImpl(
            availedPtoService = availedPtoService,
            storeUserInfo = storeUserInfo
        )
    }
}