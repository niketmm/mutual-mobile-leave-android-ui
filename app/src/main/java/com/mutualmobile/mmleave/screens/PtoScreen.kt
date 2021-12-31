package com.mutualmobile.mmleave.screens

import android.app.DatePickerDialog
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
import com.mutualmobile.mmleave.R.drawable
import com.mutualmobile.mmleave.compose.components.TopAppBarLayout
import com.mutualmobile.mmleave.ui.theme.MMLeaveTheme
import com.mutualmobile.mmleave.ui.theme.button_unselected
import com.mutualmobile.mmleave.ui.theme.primary_1
import com.mutualmobile.mmleave.ui.theme.secondary_accent_1
import com.mutualmobile.mmleave.ui.theme.white_background
import com.mutualmobile.mmleave.ui.theme.white_two
import java.util.Calendar
import java.util.Date

class PtoScreen : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      MMLeaveTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
          PtoRequestScreen()
        }
      }
    }
  }
}

@Preview
@Composable
fun ApplyPtoScreen() {
  Scaffold(
      topBar = { TopAppBarLayout() }
  ) {
    val context = LocalContext.current
    val leavesLeft = 18
    var dateFrom by remember { mutableStateOf("") }
    var dateTo by remember { mutableStateOf("") }
    var leaveDescriptionText by remember { mutableStateOf("") }
    val selected = remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }

    if (showDatePicker) {
      ShowDatePicker(context = context)
    }
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(start = 16.dp, end = 16.dp)
    ) {
      val (selectDatesText, dateFromTf, dateToTf, noOfLeavesLeft,
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
          value = dateFrom,
          onValueChange = { dateFrom = it },
          label = { Text("From") },
          trailingIcon = {
            Icon(
                painter = painterResource(id = drawable.calendar_3x),
                contentDescription = "date from text field",
                modifier = Modifier.clickable(enabled = true, onClick = {
                  showDatePicker = !showDatePicker
                })
            )
          },
          modifier = Modifier
              .fillMaxWidth(0.45F)
              .background(color = white_background)
              .clickable {
                showDatePicker = !showDatePicker
              }
              .constrainAs(dateFromTf) {
                linkTo(top = selectDatesText.bottom, bottom = parent.bottom, bias = 0.02F)
                absoluteRight.linkTo(dash.absoluteLeft)
                absoluteLeft.linkTo(parent.absoluteLeft)
              },
      )
      Text(
          "-",
          modifier = Modifier
              .fillMaxWidth(0.1F)
              .constrainAs(dash) {
                linkTo(top = selectDatesText.bottom, bottom = parent.bottom, bias = 0.05F)
                absoluteLeft.linkTo(dateFromTf.absoluteRight)
                absoluteRight.linkTo(dateToTf.absoluteLeft)
              },
          textAlign = TextAlign.Center
      )

      OutlinedTextField(
          value = dateTo,
          onValueChange = { dateTo = it },
          label = { Text("To") },
          trailingIcon = {
            Icon(
                painter = painterResource(id = drawable.calendar_3x),
                contentDescription = "date to text field",
                modifier = Modifier.clickable(enabled = true, onClick = {
                  showDatePicker = !showDatePicker
                })
            )
          },
          modifier = Modifier
              .fillMaxWidth(0.45F)
              .background(color = white_background)
              .constrainAs(dateToTf) {
                linkTo(top = selectDatesText.bottom, bottom = parent.bottom, bias = 0.02F)
                absoluteLeft.linkTo(dash.absoluteRight)
                absoluteRight.linkTo(parent.absoluteRight)
              }
      )
      Text(
          text = "Leaves Left: $leavesLeft",
          modifier = Modifier.constrainAs(noOfLeavesLeft) {
            linkTo(top = dateToTf.bottom, bottom = parent.bottom, bias = 0.03F)
            absoluteRight.linkTo(parent.absoluteRight)
          },
          color = secondary_accent_1,
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
              .background(color = white_background)
              .constrainAs(leaveDescription) {
                linkTo(top = reasonForLeave.bottom, bottom = parent.bottom, bias = 0.03F)
                absoluteLeft.linkTo(parent.absoluteLeft)
              },
          maxLines = 5,
      )
      Button(
          onClick = {
            selected.value = !selected.value
          },
          colors = ButtonDefaults.buttonColors(
              backgroundColor = if (selected.value)
                primary_1 else button_unselected,
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
            color = if (selected.value) white_two else Color.Black
        )
      }
    }

  }
}

@Composable
fun ShowDatePicker(context: Context) {
  val mYear: Int
  val mMonth: Int
  val mDay: Int
  val now = Calendar.getInstance()
  mYear = now.get(Calendar.YEAR)
  mMonth = now.get(Calendar.MONTH)
  mDay = now.get(Calendar.DAY_OF_MONTH)
  now.time = Date()
  val dateSelected = remember { mutableStateOf("") }
  val datePickerDialog = DatePickerDialog(
      context,
      { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
        val cal = Calendar.getInstance()
        cal.set(year, month, dayOfMonth)
      }, mYear, mMonth, mDay
  )

  datePickerDialog.setOnDateSetListener { datePicker, date, month, year ->
    val selectedDate = "${datePicker.dayOfMonth}/${datePicker.month}/${datePicker.year}"
    Log.d(
        "ApplyPtoScreen", "onDateSet: " + selectedDate
    )
    dateSelected.value = selectedDate
  }
  datePickerDialog.show()
}

@Preview
@Composable
fun PtoScreenPreview() {
  MMLeaveTheme {
  }
}