package com.mutualmobile.mmleave.feature_notification.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutualmobile.mmleave.feature_pto.domain.model.MMUser
import com.mutualmobile.mmleave.di.FirebaseModule
import com.mutualmobile.mmleave.feature_notification.domain.usecase.AdminNotificationUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val adminNotificationUseCases: AdminNotificationUseCases
) : ViewModel() {

    init {
        fetchNotificationList()
    }

    private val _notificationState = mutableStateOf(NotificationState())
    var notificationState = _notificationState

    private val _mmUserDetail = MutableSharedFlow<MMUser?>()
    var mmUserDetail = _mmUserDetail


    fun onEvents(adminNotificationUiEvents: AdminNotificationUiEvents){
        when(adminNotificationUiEvents){
            is AdminNotificationUiEvents.ApprovedRequest -> {
                viewModelScope.launch {
                    adminNotificationUseCases.approvedSingleNotificationUseCase.invoke(adminNotificationUiEvents.notification)
                    _notificationState.value?.notificationList?.let {
                        adminNotificationUseCases.updateApprovedReqForAllAdmins.invoke(it)
                    }
                }
            }

            is AdminNotificationUiEvents.RejectRequest -> {
                viewModelScope.launch {
                    adminNotificationUseCases.rejectSingleNotificationUseCase.invoke(adminNotificationUiEvents.notification)
                    _notificationState.value?.notificationList?.let {
                        adminNotificationUseCases.updateRejectedReqForAllAdmins.invoke(it)
                    }
                }
            }
        }
    }

    private fun fetchNotificationList() {
        viewModelScope.launch {
           adminNotificationUseCases.fetchNotificationsListUseCase.invoke(
                userEmail = FirebaseModule.currentUser.toString()
            ).collect {
                _notificationState.value = notificationState.value?.copy(
                    notificationList = it
                )
            }
        }
    }

    fun fetchUserInfo(email: String) {
        viewModelScope.launch {
            adminNotificationUseCases.fetchUserDetailsForNotificationDetailUseCase.invoke(email).collect {
                mmUserDetail.emit(it)
            }
        }
    }
}