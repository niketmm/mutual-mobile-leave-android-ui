package com.mutualmobile.mmleave.feature_pto.presentation

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import com.google.firebase.auth.FirebaseAuth
import com.mutualmobile.mmleave.common_ui.components.AdminChip
import com.mutualmobile.mmleave.common_ui.components.connectivityState
import com.mutualmobile.mmleave.ui.navigation.Screen
import com.mutualmobile.mmleave.common_ui.components.CalendarView
import com.mutualmobile.mmleave.common_ui.components.SearchScreen
import com.mutualmobile.mmleave.ui.theme.backgroundLight
import com.mutualmobile.mmleave.ui.theme.purpleTextColorLight
import com.mutualmobile.mmleave.util.ConnectionState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@ExperimentalCoilApi
@ExperimentalFoundationApi
@ExperimentalCoroutinesApi
@Composable
fun ApplyPtoScreen(
    applyPtoViewModel: ApplyPtoViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    // For connectivity states
    val context = LocalContext.current
    val connection by connectivityState()
    val isConnected = connection === ConnectionState.Available

    // For Screen scaffold states
    val scrollableState = rememberScrollState()
    val scaffoldState = rememberScaffoldState()

    // For Events and States
    val ptoLeft = applyPtoViewModel.userPtoLeftState.collectAsState()
    val applyPtoState = applyPtoViewModel.ptoUiStates.value

    applyPtoViewModel.getUserPtoLeft()

    LaunchedEffect(key1 = true) {
        applyPtoViewModel.uiEvents.collectLatest { event ->
            when (event) {
                SavePtoRequestEvents.SavedPto -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = "PTO applied Successfully"
                    )
                    delay(1000)
                    navHostController.popBackStack()
                    navHostController.navigate(Screen.Home.route)
                }
                is SavePtoRequestEvents.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (applyPtoViewModel.isValidPtoRequest(
                            applyPtoViewModel.ptoUiStates.value.localDateList,
                            applyPtoViewModel.adminListState.value.selectedAdminList
                        ) && isConnected
                    ) {
                        applyPtoViewModel.updatePtoList(
                            ptoList = applyPtoViewModel.ptoUiStates.value.localDateList,
                            email = FirebaseAuth.getInstance().currentUser?.email,
                            desc = applyPtoState.leaveDescriptionText,
                            selectedAdmins = applyPtoViewModel.adminListState.value.selectedAdminList
                        )
                        // Making the Network Call here
                        applyPtoViewModel.onEvents(ApplyPtoEvent.ApplyForPto)

                    } else {
                       Toast.makeText(context,"Some properties are blank",Toast.LENGTH_SHORT).show()
                    }
                }
            ) {
                if (isConnected)
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "success_check_pto")
                else
                    Icon(imageVector = Icons.Filled.Cancel, contentDescription = "success_check_pto")
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollableState)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(start = 16.dp, end = 16.dp)
            ) {
                SearchScreen(applyPtoViewModel)

                Spacer(modifier = Modifier.height(8.dp))

                CalendarView(applyPtoViewModel)

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 8.dp)
                ) {
                    Text(
                        text = "Leaves Left: ${ptoLeft.value}",
                        color = purpleTextColorLight,
                        fontSize = 16.sp, modifier = Modifier
                            .padding(top = 4.dp, bottom = 4.dp)
                    )
                }
                Text(
                    text = "Add Reason Of Leave (Optional)",
                    fontSize = 16.sp, modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 8.dp)
                )

                Column(
                    // Todo Remove this Column
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = applyPtoState.leaveDescriptionText.toString(),
                        onValueChange = {
                           applyPtoViewModel.onEvents(ApplyPtoEvent.AddDescription(it))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .background(color = backgroundLight),
                        maxLines = 5,
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 4.dp)
                ) {
                    LazyRow(content = {
                        items(applyPtoViewModel.adminListState.value.selectedAdminList) { admin ->
                            admin?.let { it1 -> AdminChip(mmUser = it1) }
                        }
                    })
                }
            }
        }
    }
}

