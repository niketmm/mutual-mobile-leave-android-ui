package com.mutualmobile.mmleave.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import io.github.boguszpawlowski.composecalendar.SelectableCalendar
import io.github.boguszpawlowski.composecalendar.rememberSelectableCalendarState
import io.github.boguszpawlowski.composecalendar.selection.SelectionMode
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.time.LocalDate

@ExperimentalCoroutinesApi
@Composable
fun StaticHomeCalendar(
    viewModel: HomeScreenViewModel
) {

    LocalContext.current

    val state by viewModel.allPtoSelectedList

    val dateState by viewModel.state.collectAsState()

    val dateList = listOf<LocalDate>(
        LocalDate.of(2022, 1, 13),
        LocalDate.of(2022, 2, 28),
        LocalDate.of(2022, 3, 1)
    )

    SelectableCalendar(
        calendarState = rememberSelectableCalendarState(
            initialSelection = dateList,
            initialSelectionMode = SelectionMode.Multiple,
        )
    )
}

@Preview(showBackground = true)
@Composable
fun StaticHomeCalendarPreview() {

}