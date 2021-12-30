package com.mutualmobile.mmleave.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mutualmobile.mmleave.screens.AnimatedSplashScreen

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
        ){
            Text(text = "Google Sign In", fontSize = 40.sp, style = MaterialTheme.typography.h1, modifier = Modifier.fillMaxSize())
        }
    }
}