package com.mutualmobile.mmleave.screens.pto

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.mutualmobile.mmleave.compose.components.TopAppBarLayout
import com.mutualmobile.mmleave.screens.home.CalendarView
import com.mutualmobile.mmleave.screens.pto.viewmodel.PtoRequestViewModel
import com.mutualmobile.mmleave.ui.theme.MMLeaveTheme
import com.mutualmobile.mmleave.ui.theme.backgroundLight
import com.mutualmobile.mmleave.ui.theme.primaryColorLight
import com.mutualmobile.mmleave.ui.theme.purpleTextColorLight
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

const val DEFAULT_PATTERN = "dd/MM/yyyy HH:mm"

@AndroidEntryPoint
class PtoScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MMLeaveTheme {
                val context = LocalContext.current
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    ApplyPtoScreen()
                }
            }
        }
    }
}

@ExperimentalCoroutinesApi
@Composable
fun ApplyPtoScreen(ptoViewModel: PtoRequestViewModel = hiltViewModel()) {
    Scaffold(
        topBar = { TopAppBarLayout() }
    ) {
        val context = LocalContext.current
        val leavesLeft = 18

        var leaveDescriptionText by remember { mutableStateOf("") }

        if (!ptoViewModel.ptoRequestStatus.value) {
            Toast.makeText(
                context,
                "You have already applied for PTO within this date range",
                Toast.LENGTH_SHORT
            ).show()
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(start = 16.dp, end = 16.dp)
        ) {
            Text(
                text = "Select Dates",
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
            )

            CalendarView(
                ptoViewModel,
                FirebaseAuth.getInstance().currentUser?.email!!,
                leaveDescriptionText
            )

            /*Column {
              DateFrom(ptoProp, context, ptoViewModel)

              DateTo(ptoProp, context, ptoViewModel)
            }*/

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Leaves Left: $leavesLeft",
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
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxHeight()
            ) {
                OutlinedTextField(
                    value = leaveDescriptionText,
                    onValueChange = {
                        leaveDescriptionText = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .background(color = backgroundLight),
                    maxLines = 5,
                )
                ApplyPtoButton(ptoViewModel, leaveDescriptionText)
            }
        }

    }
}

@ExperimentalCoroutinesApi
@Composable
private fun ApplyPtoButton(
    ptoViewModel: PtoRequestViewModel,
    leaveDescriptionText: String
) {
    Button(
        onClick = {
            requestPtoNow(
                ptoViewModel,
                FirebaseAuth.getInstance().currentUser?.email!!,
                leaveDescriptionText
            )
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = primaryColorLight
        ),
        modifier = Modifier.padding(bottom = 40.dp)
    ) {
        Text(
            text = "APPLY PTO",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            color = Color.White
        )
    }
}

@ExperimentalCoroutinesApi
private fun requestPtoNow(
    ptoViewModel: PtoRequestViewModel,
    email: String,
    leaveDescriptionText: String
) {
    ptoViewModel.applyPtoRequest(email, leaveDescriptionText)
}

@Preview
@Composable
fun PtoScreenPreview() {
    MMLeaveTheme {
    }
}