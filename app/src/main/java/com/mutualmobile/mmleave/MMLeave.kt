package com.mutualmobile.mmleave

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.mutualmobile.mmleave.navigation.SetUpNavGraph
import com.mutualmobile.mmleave.screens.auth.AuthViewModel
import com.mutualmobile.mmleave.ui.theme.MMLeaveTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.*

@AndroidEntryPoint
@ExperimentalCoroutinesApi
@ExperimentalFoundationApi
@ExperimentalCoilApi
@ExperimentalMaterialApi
class MMLeave : ComponentActivity() {

  private val authViewModel : AuthViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val isUserAuthenticatedPref = authViewModel.isUserAuthenticated.value

    /* This method being provided by the New Splash Screen API
     * `setKeepOnScreenCondition` will keep the Vector at Hold until we get the
     *  result back from the viewModel.
     */
    installSplashScreen().apply {
      setKeepOnScreenCondition {
        isUserAuthenticatedPref
      }
    }

    /**
     * We Move forward only when the splash Screen has been
     * Completed its works
     */
    setContent {
      MMLeaveTheme {
        Surface(color = MaterialTheme.colors.background) {
          val navController = rememberNavController()
          SetUpNavGraph(
            navController = navController,
            isAuthenticated = isUserAuthenticatedPref
          )
        }
      }
    }
  }
}