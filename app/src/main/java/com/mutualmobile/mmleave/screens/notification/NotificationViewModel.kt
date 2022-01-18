package com.mutualmobile.mmleave.screens.notification

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.mutualmobile.mmleave.data.data_state.NotificationState
import com.mutualmobile.mmleave.data.model.MMUser
import com.mutualmobile.mmleave.data.model.NotificationModel
import com.mutualmobile.mmleave.di.FirebaseModule
import com.mutualmobile.mmleave.services.database.notification.MyAdminNotificationServiceImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val myAdminNotificationServiceImpl: MyAdminNotificationServiceImpl
) : ViewModel() {

    private val _notificationState = mutableStateOf<NotificationState?>(null)
    var notificationState = _notificationState

    private val _notificationList = MutableStateFlow<List<NotificationModel?>>(emptyList())
    var notificationList = _notificationList

    private val _mmUserDetail = MutableSharedFlow<MMUser?>()
    var mmUserDetail = _mmUserDetail

    fun approvePtoRequest(notificationModel: NotificationModel) {
        viewModelScope.launch {
            myAdminNotificationServiceImpl.approvePtoRequest(notificationModel = notificationModel)
        }
    }

    fun rejectPtoRequest(notificationModel: NotificationModel) {
        viewModelScope.launch {
            myAdminNotificationServiceImpl.rejectPtoRequest(notificationModel = notificationModel)
        }
    }

    fun fetchNotificationList() {
        viewModelScope.launch {
            myAdminNotificationServiceImpl.fetchNotificationsWhere(
                adminEmailId = FirebaseModule.currentUser
//                adminEmailId = "anmol.verma@mutualmobile.com"
            ).collect {
                _notificationList.emit(it)
            }
        }
    }

    fun fetchUserInfo(email: String) {
        viewModelScope.launch {
            myAdminNotificationServiceImpl.fetchMMUserDetails(email = email).collect {
                mmUserDetail.emit(it)
            }
        }
    }
}