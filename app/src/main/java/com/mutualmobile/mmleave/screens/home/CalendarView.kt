package com.mutualmobile.mmleave.screens.home

import android.util.Log
import androidx.compose.runtime.Composable
import com.mutualmobile.mmleave.screens.pto.viewmodel.PtoRequestViewModel
import io.github.boguszpawlowski.composecalendar.SelectableCalendar
import io.github.boguszpawlowski.composecalendar.rememberSelectableCalendarState
import io.github.boguszpawlowski.composecalendar.selection.SelectionMode
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.time.LocalDate

@ExperimentalCoroutinesApi
@Composable
fun CalendarView(
    ptoViewModel: PtoRequestViewModel,
    email : String?,
    description : String?
) {
  SelectableCalendar(
      calendarState = rememberSelectableCalendarState(
          initialSelection = listOf(LocalDate.now()),
          initialSelectionMode = SelectionMode.Multiple,
          onSelectionChanged = { selectedDates ->
            Log.d("CalenderView", "CalendarView: "+selectedDates.size)
            ptoViewModel.updatePtoList(selectedDates,email,description)
          }
      )
  )
}