package com.mutualmobile.mmleave.feature_notification.domain.usecase

import com.mutualmobile.mmleave.feature_notification.domain.model.NotificationModel
import com.mutualmobile.mmleave.feature_notification.domain.repository.AdminNotificationRepository
import javax.inject.Inject

class RejectSingleNotificationUseCase @Inject constructor(
    private val adminNotificationRepository: AdminNotificationRepository
) {
    suspend operator fun invoke(
        rejectedNotificationModel: NotificationModel
    ) = adminNotificationRepository.rejectPtoRequest(rejectNotification = rejectedNotificationModel)
}