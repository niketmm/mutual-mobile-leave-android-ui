package com.mutualmobile.mmleave.di.notification

import com.mutualmobile.mmleave.services.database.notification.MyAdminNotificationServiceImpl
import com.mutualmobile.mmleave.feature_pto.data.service.NotificationRequesterImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Module
@InstallIn(SingletonComponent::class)
@ExperimentalCoroutinesApi
object NotificationModule {

    @Provides
    fun provideAdminNotificationSaverService() : MyAdminNotificationServiceImpl {
        return MyAdminNotificationServiceImpl()
    }
}