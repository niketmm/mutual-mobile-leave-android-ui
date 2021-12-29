package com.mutualmobile.mmleave.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mutualmobile.mmleave.R
import com.mutualmobile.mmleave.navigation.Screen

@Composable
fun HomeScreen(
    navController: NavHostController
) {

    val constraints = ConstraintSet {
        val topProfileAndGreetingLayout = createRefFor("topProfileAndGreetingLayout")
        val upperMidTotalLeaveLayout = createRefFor("upperMidTotalLeaveLayout")
        val lowerMidAppliedLeaveLayout = createRefFor("lowerMidAppliedLeaveLayout")
        val lowerFooterLayout = createRefFor("lowerFooterLayout")

        val midGuideline = createGuidelineFromTop(0.3f)

        constrain(topProfileAndGreetingLayout) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(upperMidTotalLeaveLayout) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(midGuideline)
        }

        constrain(lowerMidAppliedLeaveLayout) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(midGuideline)
        }

        constrain(lowerFooterLayout) {
            start.linkTo(parent.start)
            bottom.linkTo(parent.bottom)
        }

    }

    ConstraintLayout(constraintSet = constraints, modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.width(24.dp))
        Row(
            modifier = Modifier
                .layoutId("topProfileAndGreetingLayout")
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column {
                Text(text = "Good Morning", fontSize = 16.sp)
                Text(text = "Laksh", fontSize = 24.sp)
            }

            Icon(imageVector = Icons.Default.Notifications, contentDescription = "calendar icon")

            // Todo Change it with the Coil implementation
            Icon(imageVector = Icons.Default.Person, contentDescription = "person_icon")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Surface(
            modifier = Modifier
                .layoutId("upperMidTotalLeaveLayout")
                .clickable { navController.navigate(Screen.Splash.route) },
            shape = MaterialTheme.shapes.large,
            color = MaterialTheme.colors.primary
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(text = "18 of 24", fontSize = 32.sp)
                    Text(text = "PTOs availed")
                    Text(text = "SEE DETAILS -->")
                }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    LeaveAnimatedCircularProgressBar(
                        percentage = 1f,
                        totalValue = 100
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .layoutId("lowerMidAppliedLeaveLayout")
        ) {
            Text(text = "Applied PTOs")
            Surface(
                modifier = Modifier.clickable { navController.navigate(Screen.Splash.route) },
                shape = MaterialTheme.shapes.large,
                color = MaterialTheme.colors.secondary
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    // Date and Approved Btn will be at same row 
                    Row {
                        Text(text = "Feb 20,2021 - Feb 25,2021 ", fontSize = 18.sp)
                        Text(text = "Approved", fontSize = 18.sp)
                    }
                    Text(text = stringResource(id = R.string.long_text), maxLines = 2)
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .layoutId("lowerFooterLayout")
        ) {
            Row {
                Image(
                    painterResource(id = R.drawable.home_page_illus_mm_leave),
                    contentDescription = "two_people_illustration",
                    modifier = Modifier.height(156.dp)
                        .width(256.dp),
                )
                Button(onClick = { navController.navigate(Screen.Splash.route) }) {
                    Text(text = "APPLY PTO")
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "forward arrow for the button"
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}

@Composable
fun LeaveAnimatedCircularProgressBar(
    fontSize: TextUnit = 25.sp,
    radius: Dp = 50.dp,
    animationDelay: Int = 0,
    animationDuration: Int = 1500,
    stokeWidth: Dp = 8.dp,
    color: Color = Color.Blue,
    percentage: Float,
    totalValue: Int
) {

    var animationPlayed by remember {
        mutableStateOf(false)
    }

    val currentPercentage = animateFloatAsState(
        targetValue = if (animationPlayed) percentage else 0F,
        animationSpec = tween(
            durationMillis = animationDuration,
            delayMillis = animationDelay
        )
    )

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    // Box Which holds the Animation and TV
    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(radius * 2)) {
        Canvas(modifier = Modifier.size(radius * 2), onDraw = {
            // Method Provided in the DrawScope by Canvas
            drawArc(
                color = color,
                startAngle = 30f,
                360 * currentPercentage.value,
                false,
                style = Stroke(stokeWidth.toPx(), cap = StrokeCap.Round)
            )
        })
        Text(
            text = (currentPercentage.value * totalValue).toInt().toString(),
            color = color,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold
        )
    }
}