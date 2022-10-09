package com.mutualmobile.mmleave.feature_home.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.mutualmobile.mmleave.ui.MMLeave
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalCoilApi
@ExperimentalCoroutinesApi
class RandomTest {

    @get: Rule
    val composeRule = createAndroidComposeRule<MMLeave>()

    @Test
    fun aRandomTestForTesting() {
        composeRule.setContent {
            val navController = rememberNavController()
            HomeScreen(navController = navController)
        }
    }
}