package com.mutualmobile.mmleave.screens.auth

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mutualmobile.mmleave.R
import com.mutualmobile.mmleave.navigation.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LandingPageScreen(
    navController : NavHostController,
    modifier: Modifier = Modifier,
    text: String = "Continue with Google",
    loadingText: String = "Signing up...",
    shape: Shape = MaterialTheme.shapes.large,
    borderColor: Color = Color.LightGray,
    backgroundColor: Color = MaterialTheme.colors.primary,
    progressIndicatorColor: Color = Color.White,
    onClicked: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    var clicked by remember { mutableStateOf(false) }
    val scope =  rememberCoroutineScope()

    val constraint = ConstraintSet {
        val topLayout = createRefFor("top_layout")
        val midLayout = createRefFor("mid_layout")
        val endLayout = createRefFor("end_layout")

        // Creating a Guideline here for the MidLayout
        val guidelineFromTopToMid = createGuidelineFromTop(0.4f)
        val guideLineFromBottomToMid = createGuidelineFromBottom(0.05f)

        constrain(topLayout) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(midLayout){
            start.linkTo(parent.start)
            bottom.linkTo(guidelineFromTopToMid)
        }

        constrain(endLayout){
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(guideLineFromBottomToMid)
        }

    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(painterResource(id = R.drawable.rectangle_gradient_png_large), contentDescription = "rectangle_gradient_image" )
        ConstraintLayout(constraintSet = constraint, modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .layoutId("top_layout")
                    .fillMaxWidth()
                    .padding(36.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(painterResource(id = R.drawable.mm_logo_white_small), contentDescription = "top_layout_mm_logo_left", modifier = Modifier.size(28.dp), tint = Color.Unspecified)
                Text(text = "MM PTOs", color = Color.White)
            }

            Column(
                modifier = Modifier
                    .layoutId("mid_layout")
                    .fillMaxWidth()
                    .padding(start = 36.dp)
            ) {
                Text(text = "Hello!", style = MaterialTheme.typography.h2, color = Color.White)
                Spacer(modifier = Modifier.padding(8.dp))
                Text(text = "To get started,please login", style = MaterialTheme.typography.subtitle1, color = Color.White)
                Text(text = "with your MM Gmail ID.", style = MaterialTheme.typography.subtitle1, color = Color.White)
            }

            Column(
                modifier = Modifier
                    .layoutId("end_layout")
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    modifier = modifier
                        .clickable { clicked = !clicked }
                        .height(48.dp),
                    shape = shape,
                    border = BorderStroke(width = 1.dp, color = borderColor),
                    color = backgroundColor
                ) {
                    Row(
                        modifier = Modifier
                            .padding(
                                start = 12.dp,
                                end = 16.dp,
                                top = 12.dp,
                                bottom = 12.dp
                            )
                            .animateContentSize(
                                animationSpec = tween(
                                    durationMillis = 300,
                                    easing = LinearOutSlowInEasing
                                )
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painterResource(id = R.drawable.google_icon_color),
                            contentDescription = "Google Button",
                            tint = Color.Unspecified,
                            modifier = Modifier
                                .width(16.dp)
                                .height(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = if (clicked) loadingText else text)
                        if (clicked) {
                            Spacer(modifier = Modifier.width(16.dp))
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .height(16.dp)
                                    .width(16.dp),
                                strokeWidth = 2.dp,
                                color = progressIndicatorColor
                            )
                            onClicked()
                        }
                        scope.launch {
                            if (clicked) {
                                delay(4000)
                                navController.navigate(Screen.Home.route)
                                viewModel.authUser()
                            }
                        }
                    }
                }

                // Todo Why this Spacer is not working with Width??
                Spacer(modifier = Modifier.padding(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "NEED HELP? ")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "CONTACT SUPPORT", Modifier.clickable {
                        // Todo Add a Web Link here
                    },
                    textDecoration = TextDecoration.Underline
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun LandingPagePreview() {
    LandingPageScreen(
        navController = rememberNavController(),
        text = "Sign Up with Google",
        loadingText = "Creating Account...",
        onClicked = {}
    )
}