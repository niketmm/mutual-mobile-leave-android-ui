package com.mutualmobile.mmleave.feature_notification.domain.usecase

import com.mutualmobile.mmleave.feature_notification.domain.model.NotificationModel
import com.mutualmobile.mmleave.feature_notification.domain.repository.AdminNotificationRepository
import javax.inject.Inject

class UpdateRejectedReqForAllAdmins @Inject constructor(
    private val adminNotificationRepository: AdminNotificationRepository
) {
    suspend operator fun invoke(
        rejectedNotificationModel: List<NotificationModel?>
    ) = adminNotificationRepository.rejectPtoRequestForAllOtherAdmin(
        rejectNotificationList = rejectedNotificationModel
    )
}