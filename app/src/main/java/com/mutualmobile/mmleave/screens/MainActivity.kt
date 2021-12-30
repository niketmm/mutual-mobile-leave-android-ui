package com.mutualmobile.mmleave

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.mutualmobile.mmleave.navigation.SetUpNavGraph
import com.mutualmobile.mmleave.ui.theme.MMLeaveTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MMLeaveTheme {
                val navController = rememberNavController()
                SetUpNavGraph(navController)
            }
        }
    }
}