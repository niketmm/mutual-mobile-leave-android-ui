package com.mutualmobile.mmleave.di.notification

import com.mutualmobile.mmleave.feature_notification.data.notification.MyAdminNotificationServiceImpl
import com.mutualmobile.mmleave.feature_notification.data.repository.AdminNotificationRepositoryImpl
import com.mutualmobile.mmleave.feature_notification.domain.repository.AdminNotificationRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AdminNotificationRepositoryModule {

    @Binds
    @Singleton
    abstract fun provideAdminNotificationRepository(
        adminNotificationRepositoryImpl: AdminNotificationRepositoryImpl
    ) : AdminNotificationRepository
}