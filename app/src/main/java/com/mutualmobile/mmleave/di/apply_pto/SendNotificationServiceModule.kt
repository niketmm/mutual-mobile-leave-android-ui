package com.mutualmobile.mmleave.di.apply_pto

import com.mutualmobile.mmleave.feature_pto.data.service.NotificationRequester
import com.mutualmobile.mmleave.feature_pto.data.service.NotificationRequesterImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SendNotificationServiceModule {

    @Binds
    @Singleton
    abstract fun provideSendNotificationService(
        notificationRequesterImpl: NotificationRequesterImpl
    ) : NotificationRequester
}