package com.mutualmobile.mmleave.feature_notification.domain.usecase

data class AdminNotificationUseCases(
    val approvedSingleNotificationUseCase: ApprovedSingleNotificationUseCase,
    val fetchNotificationsListUseCase: FetchNotificationsListUseCase,
    val fetchUserDetailsForNotificationDetailUseCase: FetchUserDetailsForNotificationDetailUseCase,
    val rejectSingleNotificationUseCase: RejectSingleNotificationUseCase,
    val updateApprovedReqForAllAdmins: UpdateApprovedReqForAllAdmins,
    val updateFirebaseNotificationDocsUseCases: UpdateFirebaseNotificationDocsUseCases,
    val updateRejectedReqForAllAdmins: UpdateRejectedReqForAllAdmins
)
