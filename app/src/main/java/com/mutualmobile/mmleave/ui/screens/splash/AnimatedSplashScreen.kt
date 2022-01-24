package com.mutualmobile.mmleave.ui.screens.splash

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mutualmobile.mmleave.R
import com.mutualmobile.mmleave.ui.navigation.Screen
import com.mutualmobile.mmleave.viewmodels.AuthViewModel
import kotlinx.coroutines.delay

@Composable
fun AnimatedSplashScreen(
    navController: NavHostController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    viewModel.getUserAuthState()

    val userAuthState by viewModel.userAuthState.collectAsState(false)

    var startAnimation by remember {
        mutableStateOf(false)
    }

    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 1000
        )
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(3000)
        navController.popBackStack()
        if (userAuthState) {
            navController.navigate(Screen.Home.route)
        } else {
            navController.navigate(Screen.SignUp.route)
        }
    }

    Splash(alphaAnim.value)
}

@Composable
fun Splash(alpha: Float) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface),
        contentAlignment = Alignment.Center
    ) {

        Icon(
            painterResource(id = R.drawable.mm_splash_logo),
            contentDescription = stringResource(R.string.splash_screen_mm_logo),
            modifier = Modifier
                .size(52.dp)
                .alpha(alpha)
        )
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    Splash(alpha = 1f)
}