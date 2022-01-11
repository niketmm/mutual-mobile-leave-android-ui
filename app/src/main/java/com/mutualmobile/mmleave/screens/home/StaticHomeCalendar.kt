package com.mutualmobile.mmleave.screens.home

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.github.boguszpawlowski.composecalendar.SelectableCalendar
import io.github.boguszpawlowski.composecalendar.rememberSelectableCalendarState
import io.github.boguszpawlowski.composecalendar.selection.SelectionMode.Multiple
import java.time.LocalDate

@Composable
fun StaticHomeCalendar(toSelectDates: List<LocalDate>) {
  SelectableCalendar(
      calendarState = rememberSelectableCalendarState(
          initialSelection = toSelectDates,
          initialSelectionMode = Multiple,
          onSelectionChanged = { selectedDates ->
            Log.d("CalenderView", "CalendarView: " + selectedDates.size)
          }
      )
  )
}

@Preview(showBackground = true)
@Composable
fun StaticHomeCalendarPreview() {
  StaticHomeCalendar(listOf(LocalDate.now()))
}