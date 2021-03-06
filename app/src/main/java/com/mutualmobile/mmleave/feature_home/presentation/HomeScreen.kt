package com.mutualmobile.mmleave.feature_home.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.util.rangeTo
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.google.firebase.auth.FirebaseAuth
import com.mutualmobile.mmleave.R
import com.mutualmobile.mmleave.common_ui.components.CalendarDetailsCard
import com.mutualmobile.mmleave.common_ui.components.ExpandCalendar
import com.mutualmobile.mmleave.common_ui.components.ExpandingText
import com.mutualmobile.mmleave.common_ui.components.HomePtoAvailedChip
import com.mutualmobile.mmleave.common_ui.components.LeaveAnimatedCircularProgressBar
import com.mutualmobile.mmleave.common_ui.components.OutlineConnectionButton
import com.mutualmobile.mmleave.common_ui.components.OutlineNotificationButton
import com.mutualmobile.mmleave.common_ui.components.ProfileImageHolder
import com.mutualmobile.mmleave.common_ui.components.StaticHomeCalendar
import com.mutualmobile.mmleave.common_ui.components.connectivityState
import com.mutualmobile.mmleave.common_ui.components.toLocalDate
import com.mutualmobile.mmleave.ui.navigation.Screen
import com.mutualmobile.mmleave.ui.theme.primaryColorLight
import com.mutualmobile.mmleave.ui.theme.secondaryTextColorDark
import com.mutualmobile.mmleave.util.ConnectionState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.*

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@ExperimentalCoilApi
@Composable
fun HomeScreen(
    navController: NavHostController,
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel()
) {
    // Todo : Better Way to Call those functions?
    homeScreenViewModel.fetchAndCacheUserData()
    homeScreenViewModel.getLatestPtoRequest()
    homeScreenViewModel.getUserPtoLeft()
    homeScreenViewModel.isUserAdmin()

    val scrollableState = rememberScrollState()
    var expandedState by remember { mutableStateOf(false) }
    val connection by connectivityState()
    val isConnected = connection === ConnectionState.Available

    val homeState = homeScreenViewModel.homeDetailsState.value
    val isUserAdmin = homeScreenViewModel.isUserAdminState.collectAsState(initial = false).value

    LaunchedEffect(Unit) { scrollableState.animateScrollTo(0) }

    Column(
        modifier = Modifier
            .wrapContentHeight()
            .verticalScroll(scrollableState),
    ) {
        Spacer(modifier = Modifier.width(24.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, top = 8.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                val name = FirebaseAuth.getInstance().currentUser?.displayName.toString()
                val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

                val time = Calendar.getInstance().time
                Log.d("Time", "HomeScreen: $time")
                Log.d("Time", "HomeScreen: $currentHour")

                val greeting : String = when (currentHour) {
                    in 7 rangeTo 12 -> {
                        "Good Morning"
                    }
                    in 12 rangeTo 18 -> {
                        "Good Afternoon"
                    }
                    else -> {
                        "Good Night"
                    }
                }
                Text(
                    text = greeting,
                    fontSize = 16.sp,
                    color = secondaryTextColorDark
                )
                Text(text = name, fontSize = 24.sp)
            }

            Row(horizontalArrangement = Arrangement.End) {
                OutlineConnectionButton(isConnected = isConnected)
                Spacer(modifier = Modifier.width(8.dp))
                if (isUserAdmin) {
                    OutlineNotificationButton(navController = navController)
                    Spacer(modifier = Modifier.width(8.dp))
                }
                ExpandCalendar(
                    onClickEvent = {
                        expandedState = !expandedState
                        homeScreenViewModel.onEvent(HomeUiEvent.ToggleCalendarSection)
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                ProfileImageHolder(
                    navHostController = navController,
                    logoutClickEvent = {
                        homeScreenViewModel.onEvent(HomeUiEvent.LogoutUser)
                    }
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier
                .padding(bottom = 12.dp, start = 16.dp,end = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 4.dp)
                    .animateContentSize(
                        animationSpec = tween(
                            durationMillis = 300,
                            easing = FastOutSlowInEasing
                        )
                    ),
                shape = MaterialTheme.shapes.medium,
            ) {
                homeState.isCalendarExpanded?.let {
                    if (it) {
                        StaticHomeCalendar(homeScreenViewModel)
                    }
                }
            }
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 4.dp)
                    .animateContentSize(
                        animationSpec = tween(
                            durationMillis = 300,
                            easing = FastOutSlowInEasing
                        )
                    ),
                shape = MaterialTheme.shapes.medium,
            ){
                if (expandedState) {
                    CalendarDetailsCard()
                }
            }
        }

        Surface(
            modifier = Modifier
                .padding(start = 24.dp, end = 24.dp, top = 8.dp, bottom = 8.dp)
                .height(158.dp)
                .clickable {
                    navController.navigate(Screen.PtoAvailed.route)
                },
            shape = MaterialTheme.shapes.large,
            color = primaryColorLight,
            ) {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .padding(start = 24.dp)
                ) {

                    Text(text = "${homeState.totalPtoLeftCached} of 24", fontSize = 40.sp, color = Color.White)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "PTOs availed",
                        fontSize = 20.sp,
                        color = secondaryTextColorDark
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "SEE DETAILS -->", fontSize = 16.sp, color = Color.White)
                }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    homeState.totalPtoLeftCached?.let {
                        val percentage = (it.toDouble() / 24)
                        LeaveAnimatedCircularProgressBar(
                            percentage = percentage.toFloat(),
                            totalValue = 100
                        )
                    }
                }
            }
        }

        // Showing a line
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .height(1.dp)
                .background(color = secondaryTextColorDark)
        ) {
            // This is just a view (Line)
        }

        Column(
            modifier = Modifier
                .padding(start = 18.dp, end = 18.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.applied_pto_heading_home_screen),
                modifier = Modifier.padding(bottom = 8.dp, start = 8.dp)
            )
            Surface(
                modifier = Modifier
                    .padding(all = 8.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(unbounded = true),
                shape = MaterialTheme.shapes.medium,
                color = primaryColorLight
            ) {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 8.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(text = homeState.latestPtoRequest?.date.toLocalDate().toString(), fontSize = 24.sp)
                        HomePtoAvailedChip(homeState.latestPtoRequest?.ptoStatus)
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    homeState.latestPtoRequest?.description?.let { desc ->
                        AnnotatedString(text = desc)
                    }?.let {
                        ExpandingText(text = it)
                    }
                }
            }
        }


        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.Start
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painterResource(id = R.drawable.home_page_illus_mm_leave),
                    contentDescription = stringResource(R.string.bottom_home_img_illustration),
                    modifier = Modifier
                        .height(200.dp)
                        .width(270.dp),
                )

                Button(modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 24.dp, end = 16.dp),
                    onClick = { navController.navigate(Screen.ApplyPto.route) }) {
                    Text(text = "APPLY PTO")
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = stringResource(R.string.forward_arrow_icon_desc)
                    )
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@ExperimentalCoilApi
@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}