package com.mutualmobile.mmleave.feature_home.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.mutualmobile.mmleave.di.home.HomeCalendarServiceModule
import com.mutualmobile.mmleave.di.home.HomeRepositoryModule
import com.mutualmobile.mmleave.di.home.SyncCacheHomeDataServiceModule
import com.mutualmobile.mmleave.feature_availed.presentation.PtoAvailedScreen
import com.mutualmobile.mmleave.feature_notification.presentation.NotificationScreen
import com.mutualmobile.mmleave.feature_pto.presentation.ApplyPtoScreen
import com.mutualmobile.mmleave.ui.MMLeave
import com.mutualmobile.mmleave.ui.navigation.Screen
import com.mutualmobile.mmleave.ui.theme.MMLeaveTheme
import com.mutualmobile.mmleave.util.UiTestTags
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Uninstalling the Main Module, We will provide our own modules
 * and Provide Mock Dependencies so that Tests doesn't affect the
 * Main Database or the Local Database
 */

@HiltAndroidTest
@UninstallModules(
    HomeCalendarServiceModule::class,
    HomeRepositoryModule::class,
    SyncCacheHomeDataServiceModule::class
)
@ExperimentalCoilApi
@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
class HomeScreenKtTest {

    /**
     * This Rule is required are required to inject modules at specific components
     * Essential step at the JVM or UI Tests
     */
    @get: Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get: Rule(order = 1)
    val composeRule = createAndroidComposeRule<MMLeave>()

    @Before
    fun setup() {
        /**
         * Init process of testing -> Inject the Mock Dependencies, Define NavGraph
         */

        hiltRule.inject()

        composeRule.setContent {
            val navController = rememberNavController()
            MMLeaveTheme {

                NavHost(
                    navController = navController,
                    startDestination = Screen.Home.route
                ) {
                    composable(route = Screen.Home.route) {
                        HomeScreen(navController = navController)
                    }

                    composable(route = Screen.ApplyPto.route) {
                        ApplyPtoScreen(navHostController = navController)
                    }

                    composable(route = Screen.PtoAvailed.route) {
                        PtoAvailedScreen()
                    }

                    composable(route = Screen.NotificationScreen.route) {
                        NotificationScreen(navHostController = navController)
                    }
                }
            }
        }
    }

    @Test
    fun clickToggleCalendarButton_calendarIsVisible() {
        // By Default the Calendar will be Collapsed
        composeRule.onNodeWithTag(UiTestTags.HOME_CALENDAR_VIEW).assertDoesNotExist()

        // We perform a Click on the Toggle Button
        composeRule.onNodeWithTag(UiTestTags.CALENDAR_TOGGLE_BUTTON).performClick()

        // Check and assert that the Calendar View is Visible
        composeRule.onNodeWithTag(UiTestTags.HOME_CALENDAR_VIEW).assertIsDisplayed()
    }

//    @Test
//    fun clickNextMonthCalendarButtonWhenCalendarIsVisible_incrementMonthWithOne() {
//
//    }
//
//    @Test
//    fun clickPreviousMonthCalendarButtonWhenCalendarIsVisible_decrementMonthWithOne() {
//
//    }
//
//    @Test
//    fun clickUserProfileImage_logoutUser_takeToLandingScreen() {
//
//    }
//
//    @Test
//    fun clickPtoDetailSurface_takeToAvailedPtoScreen() {
//
//    }
//
//    @Test
//    fun clickApplyPtoButton_takeToApplyPtoScreen() {
//
//    }
}