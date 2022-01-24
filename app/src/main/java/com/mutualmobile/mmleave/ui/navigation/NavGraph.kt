package com.mutualmobile.mmleave.ui.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import coil.annotation.ExperimentalCoilApi
import com.mutualmobile.mmleave.ui.screens.pto.PtoAvailedScreen
import com.mutualmobile.mmleave.ui.screens.pto.PtoRequestScreen
import com.mutualmobile.mmleave.ui.screens.auth.LandingPageScreen
import com.mutualmobile.mmleave.ui.screens.home.HomeScreen
import com.mutualmobile.mmleave.ui.screens.notification.NotificationScreen
import com.mutualmobile.mmleave.ui.screens.pto.ApplyPtoScreen
import com.mutualmobile.mmleave.ui.screens.search.SearchScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalCoroutinesApi
@ExperimentalCoilApi
@Composable
fun SetUpNavGraph(
    navController: NavHostController,
    isAuthenticated : Boolean = false
) {
    NavHost(
        navController = navController,
        startDestination = if (isAuthenticated) Screen.Home.route else Screen.SignUp.route
    ) {

//        composable(
//            route = Screen.Splash.route
//        ) {
//            AnimatedSplashScreen(navController = navController)
//        }

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
            ApplyPtoScreen(navHostController = navController)
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

        composable(
            Screen.SearchScreen.route
        ){
            SearchScreen()
        }

        composable(
            Screen.NotificationScreen.route
        ){
            NotificationScreen(navController)
        }
    }
}