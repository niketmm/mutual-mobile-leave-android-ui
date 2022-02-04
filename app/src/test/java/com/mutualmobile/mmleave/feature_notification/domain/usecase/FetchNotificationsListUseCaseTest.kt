package com.mutualmobile.mmleave.feature_notification.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.mutualmobile.mmleave.feature_notification.data.repository.FakeAdminNotificationRepository
import com.mutualmobile.mmleave.feature_notification.domain.repository.AdminNotificationRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class FetchNotificationsListUseCaseTest {

    private lateinit var fakeAdminNotificationRepository: AdminNotificationRepository
    private lateinit var fetchNotificationListUseCases: FetchNotificationsListUseCase

    @Before
    fun setup(){
        fakeAdminNotificationRepository = FakeAdminNotificationRepository()
        fetchNotificationListUseCases = FetchNotificationsListUseCase(fakeAdminNotificationRepository)
    }

    @Test
    fun `filter list according to username provide`(){
        runBlocking {
            val username = "anmol.verma@mm.com"
            fetchNotificationListUseCases.invoke(username).collect {
                it.forEach { model ->
                    assertThat(model?.notify_to).isEqualTo(username)
                }
            }
        }
    }
}