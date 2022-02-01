package com.mutualmobile.mmleave.feature_notification.domain.usecase

import com.mutualmobile.mmleave.feature_notification.data.notification.AdminNotificationService
import com.mutualmobile.mmleave.feature_notification.domain.repository.AdminNotificationRepository
import javax.inject.Inject

class FetchNotificationsListUseCase @Inject constructor(
    private val adminNotificationRepository: AdminNotificationRepository
) {

    suspend operator fun invoke(userEmail: String) =
        adminNotificationRepository.fetchNotificationsWhere(userEmail)

}