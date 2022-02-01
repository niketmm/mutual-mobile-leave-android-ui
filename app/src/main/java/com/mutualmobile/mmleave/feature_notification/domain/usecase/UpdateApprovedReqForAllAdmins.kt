package com.mutualmobile.mmleave.feature_notification.domain.usecase

import com.mutualmobile.mmleave.feature_notification.domain.model.NotificationModel
import com.mutualmobile.mmleave.feature_notification.domain.repository.AdminNotificationRepository
import javax.inject.Inject

class UpdateApprovedReqForAllAdmins @Inject constructor(
    private val adminNotificationRepository: AdminNotificationRepository
) {

    suspend operator fun invoke(
        approvedNotificationsList : List<NotificationModel?>
    ) = adminNotificationRepository.approvePtoRequestForAllOtherAdmin(
        approvedNotificationList = approvedNotificationsList
    )
}