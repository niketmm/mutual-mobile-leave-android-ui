package com.mutualmobile.mmleave.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign.Companion
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mutualmobile.mmleave.R.drawable
import com.mutualmobile.mmleave.data.model.PtoData
import com.mutualmobile.mmleave.ui.theme.blueTextColorLight
import com.mutualmobile.mmleave.ui.theme.secondaryTextColorDark

const val MINIMIZED_MAX_LINES = 2

@Preview
@Composable
fun PtoAvailedScreen() {
  val text =
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
  PtosList(
      ptosList = listOf(
          PtoData(
              "Feb 20, 2021 - Feb 25, 2021", "" +
              text, "Debbie Reynolds"
          ),
          PtoData(
              "Feb 20, 2021 - Feb 25, 2021", "" +
              text, "Debbie Reynolds"
          ),
          PtoData(
              "Feb 20, 2021 - Feb 25, 2021", "" +
              text, "Debbie Reynolds"
          )
      )
  )
}

@Composable
fun PtosList(ptosList: List<PtoData>) {
  LazyColumn(
      contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp),
      modifier = Modifier.padding(start = 8.dp, end = 8.dp)
  ) {
    item {
      Text(
          text = "18 of 24 PTOs Availed",
          textAlign = Companion.Start,
          style = TextStyle(
              fontWeight = FontWeight.Bold,
              fontSize = 20.sp
          )
      )
    }
    items(ptosList) { pto ->
      Box(
          modifier = Modifier
              .border(1.dp, color = Color.Gray)
      ) {
        PtoElement(pto)
      }
    }
  }
}

@Composable
fun PtoElement(data: PtoData) {
  Column(
      modifier = Modifier
          .fillMaxWidth()
          .padding(
              top = 16.dp, bottom = 16.dp,
              start = 16.dp, end = 16.dp
          )
  ) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {

      OutlinedButton(
          onClick = { /*TODO*/ },
          modifier = Modifier.size(30.dp),  //avoid the oval shape
          shape = CircleShape,
          border = BorderStroke(1.dp, Color.White),
          contentPadding = PaddingValues(0.dp),  //avoid the little icon
          colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
      ) {
        Icon(
            painterResource(id = drawable.calendar_3x),
            contentDescription = "content description"
        )
      }
      Text(
          text = data.date,
          modifier = Modifier.padding(
              bottom = 4.dp,
              start = 4.dp
          )
      )
    }
    ExpandingText(
        text = AnnotatedString(text = data.description),
        modifier = Modifier
            .padding(4.dp)
    )
    Text(
        text = "Approved By: " + data.approvedBy,
        modifier = Modifier
            .align(Alignment.End)
            .padding(bottom = 4.dp),
        style = TextStyle(color = blueTextColorLight)
    )
  }
}

@Composable
fun ExpandingText(
  modifier: Modifier = Modifier,
  text: AnnotatedString
) {
  var isExpanded by remember { mutableStateOf(false) }
  val textLayoutResultState = remember { mutableStateOf<TextLayoutResult?>(null) }
  var isClickable by remember { mutableStateOf(false) }
  var finalText by remember {
    mutableStateOf(text)
  }
  Text(
      text = finalText,
      maxLines = if (isExpanded) Int.MAX_VALUE else MINIMIZED_MAX_LINES,
      onTextLayout = { textLayoutResultState.value = it },
      modifier = modifier
          .clickable(enabled = isClickable) { isExpanded = !isExpanded }
          .animateContentSize(),
  )
  val textLayoutResult = textLayoutResultState.value
  LaunchedEffect(textLayoutResult) {
    if (textLayoutResult == null) return@LaunchedEffect

    when {
      isExpanded -> {
        val showLessString = "Show Less"
        finalText = AnnotatedString(
            text = text.toString(),
            spanStyle = SpanStyle(color = secondaryTextColorDark)
        ).plus(
            AnnotatedString(
                text = showLessString,
                spanStyle = SpanStyle(color = Color.Black)
            )
        )
      }
      !isExpanded && textLayoutResult.hasVisualOverflow -> {
        val lastCharIndex = textLayoutResult.getLineEnd(MINIMIZED_MAX_LINES - 1)
        val showMoreString = "... Show More"
        val adjustedText = text
            .substring(startIndex = 0, endIndex = lastCharIndex)
            .dropLast(showMoreString.length)
            .dropLastWhile { it == ' ' || it == '.' }

        finalText = AnnotatedString(
            text = adjustedText,
            spanStyle = SpanStyle(color = secondaryTextColorDark)
        ).plus(
            AnnotatedString(
                text = showMoreString,
                spanStyle = SpanStyle(color = Color.Black)
            )
        )
        isClickable = true
      }
    }
  }
}
