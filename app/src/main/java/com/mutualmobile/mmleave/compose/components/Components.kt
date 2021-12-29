package com.mutualmobile.mmleave.compose.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.mutualmobile.mmleave.R
import com.mutualmobile.mmleave.ui.theme.button_unselected
import com.mutualmobile.mmleave.ui.theme.white_background

@Composable
fun TopAppBarLayout() {
  TopAppBar(
      backgroundColor = white_background,
      modifier = Modifier.fillMaxWidth(),
      title = {
        Box(modifier = Modifier.fillMaxWidth()) {
          Text(text = "Apply PTO", modifier = Modifier.align(Alignment.Center))
        }
      },
  )
}

@Composable
fun DateTextField(
  dateTypeHead: String,
  description: String
) {
  var text by remember { mutableStateOf("") }

  OutlinedTextField(
      value = text,
      onValueChange = { text = it },
      label = { Text(dateTypeHead) },
      trailingIcon = {
        Icon(
            painter = painterResource(id = R.drawable.calendar),
            contentDescription = description
        )
      }
  )
}

@Composable
fun ExplainBriefTextField() {
  var leaveDescriptionText by remember { mutableStateOf("") }
  OutlinedTextField(
      value = leaveDescriptionText, onValueChange = {
    leaveDescriptionText = it
  },
      modifier = Modifier.fillMaxWidth(),
      maxLines = 5
  )
}

@Composable
fun ApplyPtoButton() {
  val selected = remember { mutableStateOf(false) }
  Button(
      onClick = { /*TODO*/ },
      colors = ButtonDefaults.buttonColors(
          backgroundColor = if (selected.value)
            Color.Blue else button_unselected
      )

  ) {
    Text(text = "APPLY PTO")
  }
}

@Composable
@Preview
fun PreviewTopAppBar() {
  TopAppBarLayout()
}