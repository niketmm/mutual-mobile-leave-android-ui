package com.mutualmobile.mmleave.screens.home

import android.util.Log
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.mutualmobile.mmleave.screens.pto.viewmodel.PtoRequestViewModel
import io.github.boguszpawlowski.composecalendar.SelectableCalendar
import io.github.boguszpawlowski.composecalendar.day.DayState
import io.github.boguszpawlowski.composecalendar.rememberSelectableCalendarState
import io.github.boguszpawlowski.composecalendar.selection.SelectionMode
import java.time.LocalDate

@Composable
fun CalendarView(ptoViewModel: PtoRequestViewModel) {
  SelectableCalendar(
      calendarState = rememberSelectableCalendarState(
          initialSelection = listOf(LocalDate.now()),
          initialSelectionMode = SelectionMode.Multiple,
          onSelectionChanged = { selectedDates ->
            Log.d("CalenderView", "CalendarView: "+selectedDates.size)
            ptoViewModel.updatePtoList(selectedDates)
          }
      ),
  )
}