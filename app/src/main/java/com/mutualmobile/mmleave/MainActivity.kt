package com.mutualmobile.mmleave

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
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