package com.mutualmobile.mmleave.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import coil.annotation.ExperimentalCoilApi
import com.mutualmobile.mmleave.screens.AnimatedSplashScreen
import com.mutualmobile.mmleave.screens.ApplyPtoScreen
import com.mutualmobile.mmleave.screens.HomeScreen
import com.mutualmobile.mmleave.screens.LandingPageScreen
import com.mutualmobile.mmleave.screens.PtoAvailedScreen
import com.mutualmobile.mmleave.screens.PtoRequestScreen
import com.mutualmobile.mmleave.screens.PtoScreen

@ExperimentalCoilApi
@Composable
fun SetUpNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(
            route = Screen.Splash.route
        ) {
            AnimatedSplashScreen(navController = navController)
        }

        composable(
            route = Screen.SignUp.route
        ) {
            LandingPageScreen(
                navController = navController,
                text = "Continue with Google",
                loadingText = "Signing up...",
                onClicked = {}
            )
        }

        composable(
            route = Screen.Home.route
        ) {
            HomeScreen(navController)
        }

        composable(
            route = Screen.ApplyPto.route
        ) {
            ApplyPtoScreen()
        }

        composable(
            route = Screen.PtoRequests.route
        ) {
            PtoRequestScreen()
        }

        composable(
            route = Screen.PtoAvailed.route
        ) {
            PtoAvailedScreen()
        }
    }
}