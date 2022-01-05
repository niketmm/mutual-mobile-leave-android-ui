package com.mutualmobile.mmleave.navigation

sealed class Screen(val route : String){
    object Splash: Screen("splash_screen")
    object SignUp : Screen("sign_up_screen")
    object Home : Screen("home_screen")
    object ApplyPto : Screen("apply_pto")
    object PtoRequests : Screen("pto_requests")
    object PtoAvailed : Screen("pto_availed")
    object SearchScreen : Screen("search_screen")
}
