package com.mutualmobile.mmleave.di.notification

import com.mutualmobile.mmleave.feature_notification.data.notification.AdminNotificationService
import com.mutualmobile.mmleave.feature_notification.data.notification.MyAdminNotificationServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@ExperimentalCoroutinesApi
abstract class AdminNotificationServiceModule {

    @Binds
    @Singleton
    abstract fun provideAdminNotificationService(
        myAdminNotificationServiceImpl: MyAdminNotificationServiceImpl
    ) : AdminNotificationService
}