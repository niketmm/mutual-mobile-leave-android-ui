package com.mutualmobile.mmleave.screens.home

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.google.firebase.auth.FirebaseAuth
import com.mutualmobile.mmleave.R
import com.mutualmobile.mmleave.compose.components.ExpandingText
import com.mutualmobile.mmleave.compose.components.HomePtoAvailedChip
import com.mutualmobile.mmleave.compose.components.LeaveAnimatedCircularProgressBar
import com.mutualmobile.mmleave.compose.components.OutlineCalendarButton
import com.mutualmobile.mmleave.compose.components.OutlineNotificationButton
import com.mutualmobile.mmleave.compose.components.ProfileImageHolder
import com.mutualmobile.mmleave.navigation.Screen
import com.mutualmobile.mmleave.ui.theme.primaryColorLight
import com.mutualmobile.mmleave.ui.theme.secondaryTextColorDark
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalCoilApi
@Composable
fun HomeScreen(
    navController: NavHostController,
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel()
) {
    val scrollableState = rememberScrollState()

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
            val name = FirebaseAuth.getInstance().currentUser?.displayName.toString()
            Column {
                Text(
                    text = stringResource(R.string.good_morning_home_screen_text),
                    fontSize = 16.sp,
                    color = secondaryTextColorDark
                )
                Text(text = name, fontSize = 24.sp)
            }

            Row(horizontalArrangement = Arrangement.End) {
                OutlineNotificationButton(navController = navController)
                Spacer(modifier = Modifier.width(8.dp))
                OutlineCalendarButton(navController)
                Spacer(modifier = Modifier.width(8.dp))
                ProfileImageHolder()
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Box(
            modifier = Modifier
                .padding(bottom = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            StaticHomeCalendar(homeScreenViewModel)
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
            ) {
                CalendarDetailsCard()
            }
        }

        Surface(
            modifier = Modifier
                .padding(start = 24.dp, end = 24.dp, top = 8.dp, bottom = 8.dp)
                .height(158.dp),
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
                    Text(text = "18 of 24", fontSize = 40.sp, color = Color.White)
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
                    LeaveAnimatedCircularProgressBar(
                        percentage = 0.77f,
                        totalValue = 100
                    )
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
                .padding(start = 24.dp, end = 24.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.applied_pto_heading_home_screen),
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Surface(
                modifier = Modifier
                    .clickable { navController.navigate(Screen.PtoRequests.route) }
                    .padding(all = 8.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(unbounded = true),
                shape = RoundedCornerShape(8.dp),
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
                        Text(text = "Feb 20, 2021 - Feb 25, 2021 ", fontSize = 14.sp)
                        HomePtoAvailedChip()
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    ExpandingText(text = AnnotatedString(text = stringResource(id = R.string.long_text)))
                }
            }
        }


        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.Start
        ) {
            Box {
                Image(
                    painterResource(id = R.drawable.home_page_illus_mm_leave),
                    contentDescription = stringResource(R.string.bottom_home_img_illustration),
                    modifier = Modifier
                        .height(200.dp)
                        .width(270.dp),
                )
            }
        }
        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ) {
            Box(
                modifier = Modifier
                    .padding(bottom = 32.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                Button(onClick = { navController.navigate(Screen.ApplyPto.route) }) {
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

@ExperimentalCoroutinesApi
@ExperimentalCoilApi
@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}