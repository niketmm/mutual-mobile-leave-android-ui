package com.mutualmobile.mmleave.di.notification

import com.mutualmobile.mmleave.services.database.notification.MyAdminNotificationServiceImpl
import com.mutualmobile.mmleave.services.database.notification.NotificationRequesterImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Module
@InstallIn(SingletonComponent::class)
@ExperimentalCoroutinesApi
object NotificationModule {

    @Provides
    fun provideNotificationRequesterService() : NotificationRequesterImpl {
        return NotificationRequesterImpl()
    }

    @Provides
    fun provideAdminNotificationSaverService() : MyAdminNotificationServiceImpl {
        return MyAdminNotificationServiceImpl()
    }
}