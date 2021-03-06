package com.mutualmobile.mmleave.feature_notification.presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import com.mutualmobile.mmleave.common_ui.components.OutlineCalendarButton
import com.mutualmobile.mmleave.common_ui.components.PtoRequestNotificationCard
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoilApi
@ExperimentalCoroutinesApi
@Composable
fun NotificationScreen(
    navHostController: NavHostController,
    notificationViewModel: NotificationViewModel = hiltViewModel(),
) {

    val notificationState = notificationViewModel.notificationState.value
    val mmUserFlow = notificationViewModel.mmUserDetail.collectAsState(null)

    Column(
        modifier = Modifier
        .padding(
            start = 16.dp,
            end = 16.dp,
            top = 8.dp,
            bottom = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "PTO Requests - ${notificationState?.notificationList?.size}", fontSize = 18.sp)
            OutlineCalendarButton(navController = navHostController)
        }

        Column(
            Modifier.background(MaterialTheme.colors.surface)
        ){
            LazyColumn {
                items(notificationState.notificationList) { notificationModel ->
                    Log.d("NotificationScreen", "NotificationScreen: ${notificationModel?.notificationDocumentId}")
                    notificationModel?.notify_from?.let { email ->
                        notificationViewModel.fetchUserInfo(email)
                    }
                    PtoRequestNotificationCard(
                        mmUser = mmUserFlow.value,
                        notificationModel = notificationModel,
                        onApprovedClicked = {
                            notificationViewModel.onEvents(AdminNotificationUiEvents.ApprovedRequest(it))
                        },
                        onRejectedClicked = {
                            notificationViewModel.onEvents(AdminNotificationUiEvents.RejectRequest(it))
                        }
                    )
                }
            }
        }
    }
}