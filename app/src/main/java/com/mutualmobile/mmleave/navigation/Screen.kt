package com.mutualmobile.mmleave.navigation

sealed class Screen(val route : String){
    object Splash: Screen("splash_screen")
    object SignUp : Screen("sign_up_screen")
    object Home : Screen("home_screen")
}
