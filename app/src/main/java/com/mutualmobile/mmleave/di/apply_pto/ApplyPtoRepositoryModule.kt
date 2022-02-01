package com.mutualmobile.mmleave.di.apply_pto

import com.mutualmobile.mmleave.feature_pto.data.repo.ApplyPtoRepositoryImpl
import com.mutualmobile.mmleave.feature_pto.domain.repository.ApplyPtoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ApplyPtoRepositoryModule {

    @Binds
    @Singleton
    abstract fun provideApplyPtoRepository(
        applyPtoRepositoryImpl: ApplyPtoRepositoryImpl
    ) : ApplyPtoRepository
}