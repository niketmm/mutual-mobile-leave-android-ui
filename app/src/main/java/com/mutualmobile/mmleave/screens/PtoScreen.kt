package com.mutualmobile.mmleave.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.auth.FirebaseAuth
import com.mutualmobile.mmleave.R.drawable
import com.mutualmobile.mmleave.compose.components.TopAppBarLayout
import com.mutualmobile.mmleave.firestore.PtoProperties
import com.mutualmobile.mmleave.services.database.ptorequest.viewmodel.PtoRequestViewModel
import com.mutualmobile.mmleave.ui.theme.MMLeaveTheme
import com.mutualmobile.mmleave.ui.theme.backgroundLight
import com.mutualmobile.mmleave.ui.theme.primaryColorLight
import com.mutualmobile.mmleave.ui.theme.purpleTextColorLight
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.concurrent.TimeUnit

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

@Composable
fun ApplyPtoScreen(ptoViewModel: PtoRequestViewModel = hiltViewModel()) {
  Scaffold(
    topBar = { TopAppBarLayout() }
  ) {
    val context = LocalContext.current
    val leavesLeft = 18

    val ptoProp = ptoViewModel.ptoRequestState.value

    var leaveDescriptionText by remember { mutableStateOf("") }

    Column(
      modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .padding(start = 16.dp, end = 16.dp)
    ) {

      Text(
        text = "Select Dates",
        fontSize = 16.sp
      )
      Column {
        DateFrom(ptoProp, context, ptoViewModel)

        DateTo(ptoProp, context, ptoViewModel)
      }

      Text(
        text = "Leaves Left: $leavesLeft",
        color = purpleTextColorLight,
        fontSize = 16.sp, modifier = Modifier.fillMaxWidth()
      )

      Text(
        text = "Add Reason Of Leave (Optional)",
        fontSize = 16.sp, modifier = Modifier.fillMaxWidth()
      )
      OutlinedTextField(
        value = leaveDescriptionText,
        onValueChange = {
          leaveDescriptionText = it
        },
        modifier = Modifier
          .fillMaxWidth().padding(vertical = 8.dp)
          .background(color = backgroundLight),
        maxLines = 5,
      )
      ApplyPtoButton(ptoViewModel, leaveDescriptionText)
    }

  }
}

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
      backgroundColor = primaryColorLight,
    ),
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

@Composable
private fun DateTo(
  ptoProp: PtoProperties,
  context: Context,
  ptoViewModel: PtoRequestViewModel
) {
  OutlinedTextField(
    value = ptoProp.dateTo.asFormattedString(),
    onValueChange = { },
    label = { Text("DateTime To") },
    readOnly = true,
    trailingIcon = {
      Icon(
        painter = painterResource(id = drawable.ic_baseline_access_time_24),
        contentDescription = "time from text field",
        modifier = Modifier.clickable(enabled = true, onClick = {
          ShowDatePicker(context = context,
            selectDate = { date: Date ->
              ptoProp.dateTo = date
              ShowTimePicker(
                context = context,
                selectTime = { time: Date ->
                  ptoProp.dateTo = time
                  ptoViewModel.ptoRequestState.value.dateTo = time
                }, ptoProp.dateTo
              )
            })
        }
        ))
    },
    modifier = Modifier
      .fillMaxWidth()
      .background(color = backgroundLight)

  )
}

@Composable
private fun DateFrom(
  ptoProp: PtoProperties,
  context: Context,
  ptoViewModel: PtoRequestViewModel
) {
  OutlinedTextField(
    value = ptoProp.dateFrom.asFormattedString(),
    onValueChange = { },
    label = { Text("DateTime From") },
    readOnly = true,
    trailingIcon = {
      Icon(
        painter = painterResource(id = drawable.calendar_3x),
        contentDescription = "date from text field",
        modifier = Modifier.clickable(enabled = true, onClick = {
          ShowDatePicker(context = context,
            selectDate = { date: Date ->
              ptoProp.dateFrom = date
              ShowTimePicker(
                context = context,
                selectTime = { time: Date ->
                  ptoProp.dateFrom = time
                  ptoViewModel.ptoRequestState.value.dateFrom = time
                }, ptoProp.dateFrom
              )
            })
        }
        ))
    },
    modifier = Modifier
      .fillMaxWidth()
      .background(color = backgroundLight)
  )
}

private fun Date?.asFormattedString(): String {
  val formatter = SimpleDateFormat(DEFAULT_PATTERN)
  return formatter.format(this)
}

private fun requestPtoNow(
  ptoViewModel: PtoRequestViewModel,
  email: String,
  leaveDescriptionText: String
) {
  ptoViewModel.applyPtoRequest(email, leaveDescriptionText)
}

fun ShowTimePicker(
  context: Context,
  selectTime: (Date) -> Unit,
  date: Date?
) {
  val mMinute: Int
  val mHour: Int
  val now = Calendar.getInstance()
  mMinute = now.get(Calendar.MINUTE)
  mHour = now.get(Calendar.HOUR_OF_DAY)
  now.time = date

  val timePickerDialog = TimePickerDialog(
    context,
    { view, hourOfDay, minute ->
      now.set(Calendar.HOUR_OF_DAY,hourOfDay)
      now.set(Calendar.MINUTE,minute)
      selectTime(now.time)
    }, mHour, mMinute, true
  )
  timePickerDialog.show()
}

fun ShowDatePicker(
  context: Context,
  selectDate: (Date) -> Unit,
) {
  val mYear: Int
  val mMonth: Int
  val mDay: Int
  val now = Calendar.getInstance()
  mYear = now.get(Calendar.YEAR)
  mMonth = now.get(Calendar.MONTH)
  mDay = now.get(Calendar.DAY_OF_MONTH)
  now.time = Date()
  val datePickerDialog = DatePickerDialog(
    context,
    { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
      val cal = Calendar.getInstance()
      cal.set(year, month, dayOfMonth)
    }, mYear, mMonth, mDay
  )

  datePickerDialog.setOnDateSetListener { datePicker, year, month, date ->
    // Create a Calendar instance
    val mCalendar = Calendar.getInstance()
    mCalendar[Calendar.YEAR] = year
    mCalendar[Calendar.MONTH] = month
    mCalendar[Calendar.DAY_OF_MONTH] = date
    selectDate(mCalendar.time)
  }
  datePickerDialog.show()
}

@Preview
@Composable
fun PtoScreenPreview() {
  MMLeaveTheme {
  }
}