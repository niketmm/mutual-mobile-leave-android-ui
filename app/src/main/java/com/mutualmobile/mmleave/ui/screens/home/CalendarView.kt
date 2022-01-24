package com.mutualmobile.mmleave.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mutualmobile.mmleave.viewmodels.PtoRequestViewModel
import io.github.boguszpawlowski.composecalendar.SelectableCalendar
import io.github.boguszpawlowski.composecalendar.rememberSelectableCalendarState
import io.github.boguszpawlowski.composecalendar.selection.SelectionMode
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.time.LocalDate

@ExperimentalCoroutinesApi
@Composable
fun CalendarView(
    ptoViewModel: PtoRequestViewModel
) {
    Column(
        modifier = Modifier.fillMaxWidth().fillMaxSize(0.6f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        SelectableCalendar(
            calendarState = rememberSelectableCalendarState(
                initialSelection = listOf(LocalDate.now()),
                initialSelectionMode = SelectionMode.Multiple,
                onSelectionChanged = { selectedDates ->
                    ptoViewModel.allPtoSelectedList.value.localDateList = selectedDates
                }
            )
        )
    }
}