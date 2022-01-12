package com.mutualmobile.mmleave.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import io.github.boguszpawlowski.composecalendar.Calendar
import io.github.boguszpawlowski.composecalendar.CalendarState
import io.github.boguszpawlowski.composecalendar.SelectableCalendar
import io.github.boguszpawlowski.composecalendar.day.DayState
import io.github.boguszpawlowski.composecalendar.day.DefaultDay
import io.github.boguszpawlowski.composecalendar.header.DefaultMonthHeader
import io.github.boguszpawlowski.composecalendar.header.MonthState
import io.github.boguszpawlowski.composecalendar.selection.DynamicSelectionState
import io.github.boguszpawlowski.composecalendar.selection.SelectionMode
import io.github.boguszpawlowski.composecalendar.selection.SelectionState
import io.github.boguszpawlowski.composecalendar.week.DefaultWeekHeader
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.WeekFields
import java.util.*

@ExperimentalCoroutinesApi
@Composable
fun StaticHomeCalendar(
  viewModel: HomeScreenViewModel
) {


  val state by viewModel.allPtoSelectedList.collectAsState()
  val dateState by viewModel.state.collectAsState()

  val states = remember {
    CalendarState(MonthState(YearMonth.now()), object : SelectionState {
      override fun isDateSelected(date: LocalDate): Boolean {
        return dateState.contains(date)
      }

      override fun onDateSelected(date: LocalDate) {

      }

    })
  }

  Calendar(
    modifier = Modifier,
    firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek,
    today = LocalDate.now(),
    calendarState = states,
    showAdjacentMonths = true,
    horizontalSwipeEnabled = true,
    dayContent = { DefaultDay(it) },
    monthHeader = { DefaultMonthHeader(it) },
    weekHeader = { DefaultWeekHeader(it) },
    monthContainer = { content ->
      Box { content(PaddingValues()) }
    }
  )

}


@Preview(showBackground = true)
@Composable
fun StaticHomeCalendarPreview() {

}