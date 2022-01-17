package com.mutualmobile.mmleave.di.notification

import com.mutualmobile.mmleave.services.database.notification.AdminNotificationServiceImpl
import com.mutualmobile.mmleave.services.database.notification.NotificationRequesterImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Module
@InstallIn(ActivityRetainedComponent::class)
@ExperimentalCoroutinesApi
object NotificationModule {

    @Provides
    fun provideNotificationRequesterService() : NotificationRequesterImpl {
        return NotificationRequesterImpl()
    }

    @Provides
    fun provideNotificationSaverService() : AdminNotificationServiceImpl {
        return AdminNotificationServiceImpl()
    }
}