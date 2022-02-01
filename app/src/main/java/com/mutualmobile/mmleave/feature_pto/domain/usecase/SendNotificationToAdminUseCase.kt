package com.mutualmobile.mmleave.feature_pto.domain.usecase

import com.mutualmobile.mmleave.data.model.NotificationModel
import com.mutualmobile.mmleave.feature_pto.domain.repository.ApplyPtoRepository
import javax.inject.Inject

class SendNotificationToAdminUseCase @Inject constructor(
    private val applyPtoRepository: ApplyPtoRepository
) {

    suspend operator fun invoke(notification : NotificationModel) {
        applyPtoRepository.sendNotificationToAdmin(notificationModel =  notification)
    }
}