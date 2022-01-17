package com.mutualmobile.mmleave.screens.notification

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import com.mutualmobile.mmleave.compose.components.OutlineCalendarButton
import com.mutualmobile.mmleave.compose.components.PtoRequestNotificationCard
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoilApi
@ExperimentalCoroutinesApi
@Composable
fun NotificationScreen(
    navHostController: NavHostController,
    notificationViewModel: NotificationViewModel = hiltViewModel(),
) {
    var totalPtoRequest by remember {
        mutableStateOf(0)
    }

    val scope = rememberCoroutineScope()
    val notificationList = notificationViewModel.notificationList.collectAsState()

    LaunchedEffect(key1 = true) {
        notificationViewModel.fetchNotificationList()
    }

    Column(modifier = Modifier
        .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)) {
        totalPtoRequest = notificationList.value.size
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "PTO Requests - $totalPtoRequest", fontSize = 18.sp)
            OutlineCalendarButton(navController = navHostController)
        }

        Column(
            Modifier.background(MaterialTheme.colors.surface)
        ){
            LazyColumn {
                items(notificationList.value) { notificationModel ->
                    PtoRequestNotificationCard(
                        notificationModel = notificationModel,
                        onApprovedClicked = {
                            notificationViewModel.approvePtoRequest(it)
                        },
                        onRejectedClicked = {
                            notificationViewModel.rejectPtoRequest(it)
                        }
                    )

//                    Log.d("NotificationScreen", "NotificationScreen: ${notificationModel?.notify_to}")
//                    notificationModel?.notify_from?.let { email ->
//                        notificationViewModel.fetchUserInfo(email)
//                    }?.let { mmUser ->
//                        PtoRequestNotificationCard(mmUser = mmUser, notificationModel = notificationModel)
//                    }
                }
            }
        }
    }
}