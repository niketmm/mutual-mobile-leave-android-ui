package com.mutualmobile.mmleave

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.mutualmobile.mmleave.R.drawable
import com.mutualmobile.mmleave.compose.components.TopAppBarLayout
import com.mutualmobile.mmleave.ui.theme.MMLeaveTheme
import com.mutualmobile.mmleave.ui.theme.button_unselected

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      MMLeaveTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
          ApplyPtoScreen()
        }
      }
    }
  }
}

@Composable
fun ApplyPtoScreen() {
  Scaffold(
      topBar = { TopAppBarLayout() }
  ) {
    val leavesLeft = 18
    var dateFrom by remember { mutableStateOf("") }
    var dateTo by remember { mutableStateOf("") }
    var leaveDescriptionText by remember { mutableStateOf("") }
    val selected = remember { mutableStateOf(false) }

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
            top.linkTo(parent.top)
            bottom.linkTo(dateFromTf.top)
            absoluteLeft.linkTo(parent.absoluteLeft)
          }
      )
      OutlinedTextField(
          value = dateFrom,
          onValueChange = { dateFrom = it },
          label = { Text("From") },
          trailingIcon = {
            Icon(
                painter = painterResource(id = drawable.calendar_3x),
                contentDescription = "date from text field"
            )
          },
          modifier = Modifier
              .fillMaxWidth(0.45F)
              .constrainAs(dateFromTf) {
                top.linkTo(selectDatesText.bottom)
                absoluteRight.linkTo(dash.absoluteLeft)
                absoluteLeft.linkTo(parent.absoluteLeft)
                bottom.linkTo(noOfLeavesLeft.top)
              }
      )
      Text(
          "-",
          modifier = Modifier
              .fillMaxWidth(0.1F)
              .constrainAs(dash) {
                top.linkTo(selectDatesText.bottom)
                absoluteLeft.linkTo(dateFromTf.absoluteRight)
                absoluteRight.linkTo(dateToTf.absoluteLeft)
                bottom.linkTo(noOfLeavesLeft.top)
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
                contentDescription = "date to text field"
            )
          },
          modifier = Modifier
              .fillMaxWidth(0.45F)
              .constrainAs(dateToTf) {
                top.linkTo(selectDatesText.bottom)
                absoluteLeft.linkTo(dash.absoluteRight)
                absoluteRight.linkTo(parent.absoluteRight)
                bottom.linkTo(noOfLeavesLeft.top)
              }
      )
      Text(text = "Leaves Left: $leavesLeft",
          modifier = Modifier.constrainAs(noOfLeavesLeft) {
            top.linkTo(dateToTf.bottom)
            absoluteRight.linkTo(parent.absoluteRight)
            bottom.linkTo(reasonForLeave.top)
          })

      Text(text = "Add Reason Of Leave (Optional)",
          modifier = Modifier.constrainAs(reasonForLeave) {
            top.linkTo(noOfLeavesLeft.bottom)
            linkTo(top = noOfLeavesLeft.bottom, bottom = parent.bottom, bias = 0.1F)
            absoluteLeft.linkTo(parent.absoluteLeft)
            bottom.linkTo(leaveDescription.top)
          })
      OutlinedTextField(
          value = leaveDescriptionText,
          onValueChange = {
            leaveDescriptionText = it
          },
          modifier = Modifier
              .fillMaxWidth()
              .constrainAs(leaveDescription) {
                top.linkTo(reasonForLeave.bottom)
                absoluteLeft.linkTo(parent.absoluteLeft)
                bottom.linkTo(applyPtoButton.top, margin = 32.dp)
              },
          maxLines = 5,
      )
      Button(
          onClick = { /*TODO*/ },
          colors = ButtonDefaults.buttonColors(
              backgroundColor = if (selected.value)
                Color.Blue else button_unselected
          ),
          modifier = Modifier.constrainAs(applyPtoButton) {
            top.linkTo(leaveDescription.bottom)
            absoluteLeft.linkTo(parent.absoluteLeft)
            bottom.linkTo(parent.bottom)
            absoluteRight.linkTo(parent.absoluteRight)
          }

      ) {
        Text(text = "APPLY PTO")
      }
    }

  }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
  MMLeaveTheme {
  }
}