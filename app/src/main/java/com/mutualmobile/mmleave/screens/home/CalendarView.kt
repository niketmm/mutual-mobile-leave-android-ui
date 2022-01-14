package com.mutualmobile.mmleave.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
    email: String?,
    description: String?
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        SelectableCalendar(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp),
            calendarState = rememberSelectableCalendarState(
                initialSelection = listOf(LocalDate.now()),
                initialSelectionMode = SelectionMode.Multiple,
                onSelectionChanged = { selectedDates ->
                    ptoViewModel.updatePtoList(selectedDates, email, description)
                }
            )
        )
    }
}