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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.mutualmobile.mmleave.R.drawable
import com.mutualmobile.mmleave.compose.components.TopAppBarLayout
import com.mutualmobile.mmleave.firestore.PtoProperties
import com.mutualmobile.mmleave.services.database.ptorequest.viewmodel.PtoRequestViewModel
import com.mutualmobile.mmleave.ui.theme.MMLeaveTheme
import com.mutualmobile.mmleave.ui.theme.backgroundLight
import com.mutualmobile.mmleave.ui.theme.buttonUnselected
import com.mutualmobile.mmleave.ui.theme.primaryColorLight
import com.mutualmobile.mmleave.ui.theme.purpleTextColorLight
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

const val DEFAULT_PATTERN = "dd/M/yyyy HH:mm:ss"

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
fun ApplyPtoScreen(
) {
  Scaffold(
      topBar = { TopAppBarLayout() }
  ) {
    val context = LocalContext.current
    // val email = FirebaseAuth.getInstance().currentUser?.email
    val email = ""
    val leavesLeft = 18
    var dateFrom: Date by remember {
      mutableStateOf(Date())
    }
    var dateTo: Date by remember {
      mutableStateOf(Date())
    }
    var timeFrom: String by remember {
      mutableStateOf("")
    }
    var timeTo: String by remember {
      mutableStateOf("")
    }
    var dateFromText by remember { mutableStateOf("") }
    var timeFromText by remember { mutableStateOf("") }
    var dateToText by remember { mutableStateOf("") }
    var timeToText by remember { mutableStateOf("") }
    var leaveDescriptionText by remember { mutableStateOf("") }
    val selected = remember { mutableStateOf(false) }
    var showDatePickerFrom by remember { mutableStateOf(false) }
    var showTimePickerFrom by remember { mutableStateOf(false) }
    var showDatePickerTo by remember { mutableStateOf(false) }
    var showTimePickerTo by remember { mutableStateOf(false) }

    if (showDatePickerFrom) {
      ShowDatePicker(context = context, 0, selectDate = { date: Date, dateType: Int ->
        dateFrom = date
      })
      showDatePickerFrom = false
    }

    if (showDatePickerTo) {
      ShowDatePicker(context = context, 1,
          selectDate = { date: Date, dateType: Int ->
            dateTo = date
            Log.d("PtoScreen", "ApplyPtoScreen: " + dateTo)
          })
      showDatePickerTo = false
    }
    if (showTimePickerFrom) {
      ShowTimePicker(context = context, timeType = 0,
          selectTime = { time: String, timeType: Int ->
            timeFrom = time
            Log.d("PtoScreen", "ApplyPtoScreen: " + timeFrom)
          })
      showTimePickerFrom = false
    }

    if (showTimePickerTo) {
      ShowTimePicker(context = context, timeType = 1,
          selectTime = { time: String, timeType: Int ->
            timeTo = time
          })
      showTimePickerTo = false
    }
    if (selected.value) {
      val formatter = SimpleDateFormat(DEFAULT_PATTERN)
      Log.d("PtoScreen", "ApplyPtoScreen: " + timeFrom)
      val selectedDateFrom = formatter.format(dateFrom)
      Log.d("PtoScreen", "ShowDatePicker: $selectedDateFrom")

      val selectedDateTo = formatter.parse(
          "${dateTo.date}/${dateTo.month + 1}/${dateTo.year} $timeTo"
      )!!
      Log.d("PtoScreen", "ShowDatePicker: $selectedDateTo")
      ApplyPto(
          ptoProperties = PtoProperties(
              selectedDateFrom, selectedDateTo, email, leaveDescriptionText
          )
      )
    }
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(start = 16.dp, end = 16.dp)
    ) {
      val (selectDatesText, dateFromTf, timeFromTf, dateToTf, timeToTf, noOfLeavesLeft,
        reasonForLeave, leaveDescription, applyPtoButton, dash) = createRefs()
      Text(
          text = "Select Dates",
          modifier = Modifier.constrainAs(selectDatesText) {
            linkTo(top = parent.top, bottom = parent.bottom, bias = 0.05F)
            absoluteLeft.linkTo(parent.absoluteLeft)
          },
          fontSize = 16.sp
      )
      OutlinedTextField(
          value = dateFromText,
          onValueChange = { dateFromText = it },
          label = { Text("From") },
          trailingIcon = {
            Icon(
                painter = painterResource(id = drawable.calendar_3x),
                contentDescription = "date from text field",
                modifier = Modifier.clickable(enabled = true, onClick = {
                  showDatePickerFrom = true
                }
                ))
          },
          modifier = Modifier
              .fillMaxWidth(0.25F)
              .background(color = backgroundLight)
              .constrainAs(dateFromTf) {
                linkTo(top = selectDatesText.bottom, bottom = parent.bottom, bias = 0.02F)
                absoluteRight.linkTo(timeFromTf.absoluteLeft)
                absoluteLeft.linkTo(parent.absoluteLeft)
              }
      )

      OutlinedTextField(
          value = timeFromText,
          onValueChange = { timeFromText = it },
          label = { Text("Time From") },
          trailingIcon = {
            Icon(
                painter = painterResource(id = drawable.ic_baseline_access_time_24),
                contentDescription = "time from text field",
                modifier = Modifier.clickable(enabled = true, onClick = {
                  showTimePickerFrom = true
                }
                ))
          },
          modifier = Modifier
              .fillMaxWidth(0.25F)
              .background(color = backgroundLight)
              .constrainAs(timeFromTf) {
                linkTo(top = selectDatesText.bottom, bottom = parent.bottom, bias = 0.02F)
                absoluteRight.linkTo(dash.absoluteLeft)
                absoluteLeft.linkTo(dateFromTf.absoluteRight, 5.dp)
              }
      )

      Text(
          "-",
          modifier = Modifier
              .fillMaxWidth(0.05F)
              .constrainAs(dash) {
                linkTo(top = selectDatesText.bottom, bottom = parent.bottom, bias = 0.05F)
                absoluteLeft.linkTo(timeFromTf.absoluteRight)
                absoluteRight.linkTo(dateToTf.absoluteLeft)
              },
          textAlign = TextAlign.Center
      )

      OutlinedTextField(
          value = dateToText,
          onValueChange = { dateToText = it },
          label = { Text("To") },
          trailingIcon = {
            Icon(
                painter = painterResource(id = drawable.calendar_3x),
                contentDescription = "date to text field",
                modifier = Modifier.clickable(enabled = true, onClick = {
                  showDatePickerTo = true
                }
                ))
          },
          modifier = Modifier
              .fillMaxWidth(0.25F)
              .background(color = backgroundLight)
              .constrainAs(dateToTf) {
                linkTo(top = selectDatesText.bottom, bottom = parent.bottom, bias = 0.02F)
                absoluteLeft.linkTo(dash.absoluteRight)
                absoluteRight.linkTo(timeToTf.absoluteLeft, 5.dp)
              },

          )

      OutlinedTextField(
          value = timeToText,
          onValueChange = { timeToText = it },
          label = { Text("Time To") },
          trailingIcon = {
            Icon(
                painter = painterResource(id = drawable.ic_baseline_access_time_24),
                contentDescription = "time to text field",
                modifier = Modifier.clickable(enabled = true, onClick = {
                  showTimePickerTo = true
                }
                ))
          },
          modifier = Modifier
              .fillMaxWidth(0.25F)
              .background(color = backgroundLight)
              .constrainAs(timeToTf) {
                linkTo(top = selectDatesText.bottom, bottom = parent.bottom, bias = 0.02F)
                absoluteLeft.linkTo(dateToTf.absoluteRight)
                absoluteRight.linkTo(parent.absoluteRight)
              },

          )
      Text(
          text = "Leaves Left: $leavesLeft",
          modifier = Modifier.constrainAs(noOfLeavesLeft) {
            linkTo(top = dateToTf.bottom, bottom = parent.bottom, bias = 0.03F)
            absoluteRight.linkTo(parent.absoluteRight)
          },
          color = purpleTextColorLight,
          fontSize = 16.sp
      )

      Text(
          text = "Add Reason Of Leave (Optional)",
          modifier = Modifier.constrainAs(reasonForLeave) {
            top.linkTo(noOfLeavesLeft.bottom)
            linkTo(top = noOfLeavesLeft.bottom, bottom = parent.bottom, bias = 0.05F)
            absoluteLeft.linkTo(parent.absoluteLeft)
          },
          fontSize = 16.sp
      )
      OutlinedTextField(
          value = leaveDescriptionText,
          onValueChange = {
            leaveDescriptionText = it
          },
          modifier = Modifier
              .fillMaxWidth()
              .background(color = backgroundLight)
              .constrainAs(leaveDescription) {
                linkTo(top = reasonForLeave.bottom, bottom = parent.bottom, bias = 0.03F)
                absoluteLeft.linkTo(parent.absoluteLeft)
              },
          maxLines = 5,
      )
      Button(
          onClick = { selected.value = !selected.value },
          colors = ButtonDefaults.buttonColors(
              backgroundColor = if (selected.value)
                primaryColorLight else buttonUnselected,
          ),
          modifier = Modifier.constrainAs(applyPtoButton) {
            linkTo(top = leaveDescription.bottom, bottom = parent.bottom, bias = 0.8F)
            absoluteLeft.linkTo(parent.absoluteLeft)
            absoluteRight.linkTo(parent.absoluteRight)
          },

          ) {
        Text(
            text = "APPLY PTO",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            color = if (selected.value) Color.White else Color.Black
        )
      }
    }

  }
}

@Composable
fun ApplyPto(
  ptoProperties: PtoProperties,
) {
  val ptoViewModel: PtoRequestViewModel = hiltViewModel()
  ptoViewModel.applyPtoRequest(ptoProperties)
}

@Composable
fun ShowTimePicker(
  context: Context,
  timeType: Int,
  selectTime: (String, timeType: Int) -> Unit
) {
  val mMinute: Int
  val mHour: Int
  val mSecond: Int
  val now = Calendar.getInstance()
  mMinute = now.get(Calendar.MINUTE)
  mHour = now.get(Calendar.HOUR)
  now.time = Date()

  val timePickerDialog = TimePickerDialog(
      context,
      { view, hourOfDay, minute ->
        selectTime("$hourOfDay:$minute:00", timeType)
      }, mHour, mMinute, true
  )
  timePickerDialog.show()
}

@Composable
fun ShowDatePicker(
  context: Context,
  dateType: Int,
  selectDate: (Date, dateType: Int) -> Unit,
) {
  val mYear: Int
  val mMonth: Int
  val mDay: Int
  val now = Calendar.getInstance()
  mYear = now.get(Calendar.YEAR)
  mMonth = now.get(Calendar.MONTH)
  mDay = now.get(Calendar.DAY_OF_MONTH)
  now.time = Date()
  val dateSelected = remember { mutableStateOf(Date()) }
  val datePickerDialog = DatePickerDialog(
      context,
      { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
        val cal = Calendar.getInstance()
        cal.set(year, month, dayOfMonth)
      }, mYear, mMonth, mDay
  )

  datePickerDialog.setOnDateSetListener { datePicker, date, month, year ->
    dateSelected.value = Date(date, month, year)
    selectDate(dateSelected.value, dateType)
    Log.d("PtoScreen", "ShowDatePicker: " + dateSelected.value)
  }
  datePickerDialog.show()
}

@Preview
@Composable
fun PtoScreenPreview() {
  MMLeaveTheme {
  }
}