package com.mutualmobile.mmleave.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.boguszpawlowski.composecalendar.StaticCalendar
import io.github.boguszpawlowski.composecalendar.day.DayState
import io.github.boguszpawlowski.composecalendar.rememberCalendarState
import io.github.boguszpawlowski.composecalendar.selection.EmptySelectionState
import io.github.boguszpawlowski.composecalendar.selection.SelectionState
import java.time.LocalDate

@Composable
fun StaticHomeCalendar(ptoDates: List<LocalDate>) {
  StaticCalendar(
      modifier = Modifier
          .fillMaxWidth()
          .wrapContentHeight(),
      calendarState = rememberCalendarState(),
      dayContent = {
        DefaultSelectedDay(
            toSelect = ptoDates.contains(it.date),
            state = it
        )
      }
  )
}

@Preview(showBackground = true)
@Composable
fun StaticHomeCalendarPreview() {
  StaticHomeCalendar(listOf(LocalDate.now()))
}

@Composable
fun <T : SelectionState> DefaultSelectedDay(
  state: DayState<T>,
  modifier: Modifier = Modifier,
  selectionColor: Color = Color.White,
  currentDayColor: Color = MaterialTheme.colors.primary,
  onClick: (LocalDate) -> Unit = {},
  toSelect: Boolean
) {
  val date = state.date
  val selectionState = state.selectionState
  val ptoDayColor = if (toSelect)
    Color.Blue
  else
    Color.White
  Card(
      modifier = modifier
          .aspectRatio(1f)
          .padding(2.dp),
      elevation = if (state.isFromCurrentMonth) 4.dp else 0.dp,
      border = if (state.isCurrentDay) BorderStroke(1.dp, currentDayColor) else null,
      contentColor = if (toSelect) selectionColor else contentColorFor(
          backgroundColor = MaterialTheme.colors.surface
      )
  ) {
    Box(
        modifier = Modifier
            .background(color = ptoDayColor)
            .clickable {
              onClick(date)
              selectionState.onDateSelected(date)
            },
        contentAlignment = Alignment.Center,
    ) {
      Text(text = date.dayOfMonth.toString())
    }
  }
}

