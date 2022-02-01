package com.mutualmobile.mmleave.common_ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mutualmobile.mmleave.feature_pto.presentation.ApplyPtoEvent
import com.mutualmobile.mmleave.feature_pto.presentation.ApplyPtoViewModel
import io.github.boguszpawlowski.composecalendar.SelectableCalendar
import io.github.boguszpawlowski.composecalendar.rememberSelectableCalendarState
import io.github.boguszpawlowski.composecalendar.selection.SelectionMode
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.time.LocalDate

@ExperimentalCoroutinesApi
@Composable
fun CalendarView(
    applyPtoViewModel: ApplyPtoViewModel
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
                    applyPtoViewModel.onEvents(ApplyPtoEvent.SelectPtoDates(selectedPtoDates = selectedDates))
                }
            )
        )
    }
}