package com.mutualmobile.mmleave.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mutualmobile.mmleave.data.model.DisplayDateModel
import com.mutualmobile.mmleave.data.model.PtoRequestDateModel
import com.mutualmobile.mmleave.data.model.FirebasePtoRequestModel
import io.github.boguszpawlowski.composecalendar.Calendar
import io.github.boguszpawlowski.composecalendar.CalendarState
import io.github.boguszpawlowski.composecalendar.day.DayState
import io.github.boguszpawlowski.composecalendar.header.DefaultMonthHeader
import io.github.boguszpawlowski.composecalendar.header.MonthState
import io.github.boguszpawlowski.composecalendar.selection.SelectionState
import io.github.boguszpawlowski.composecalendar.week.DefaultWeekHeader
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import java.time.temporal.WeekFields
import java.util.*

@ExperimentalCoroutinesApi
@Composable
fun StaticHomeCalendar(
    viewModel: HomeScreenViewModel
) {

    viewModel.setHolidayAndPtoRequestedStatusDateList()
    viewModel.setHolidayAndSelectedLocalDateList()

    // Instead of only Selected Dates we will display
    // Selected Dates + Holiday Dates
    val dateState = viewModel.allPtoSelectedList.value.holidayAndSelectedLocalDateList

    // Instead of only Fetched PTO dates with Status we will display
    // Fetched Dates + Fetched Holiday Dates
    val ptoDates = viewModel.allPtoSelectedList.value.holidayAndPtoRequestedStatusDateList

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
        dayContent = {
            DefaultSelectedDay(
                state = it,
                firebasePtoRequestModel = ptoDates.find { calendarDate ->
                    calendarDate?.date.toLocalDate() == it.date
                },
            )
        },
        monthHeader = { DefaultMonthHeader(it) },
        weekHeader = { DefaultWeekHeader(it) },
        monthContainer = { content ->
            Box { content(PaddingValues()) }
        }
    )
}

@Composable
fun <T : SelectionState> DefaultSelectedDay(
    state: DayState<T>,
    modifier: Modifier = Modifier,
    selectionColor: Color = Color.White,
    currentDayColor: Color = MaterialTheme.colors.primary,
    onClick: (LocalDate) -> Unit = {},
    firebasePtoRequestModel: DisplayDateModel?
) {
    val date = state.date
    val selectionState = state.selectionState
    val ptoDayColor = firebasePtoRequestModel?.let {
        when (it.ptoStatus) {
            PtoRequestDateModel.PtoGraphStatus.APPLIED.toString() -> Color.Green
            PtoRequestDateModel.PtoGraphStatus.APPROVED.toString() -> Color.Cyan
            PtoRequestDateModel.PtoGraphStatus.HOLIDAY.toString() -> Color.Yellow
            PtoRequestDateModel.PtoGraphStatus.REJECTED.toString() -> Color.Red
            PtoRequestDateModel.PtoGraphStatus.PENDING.toString() -> Color.Blue
            PtoRequestDateModel.PtoGraphStatus.UPDATED.toString() -> Color.Magenta
            else -> Color.White
        }
    } ?: Color.White

    Card(
        modifier = modifier
            .aspectRatio(1f)
            .padding(2.dp),
        elevation = if (state.isFromCurrentMonth) 4.dp else 0.dp,
        border = if (state.isCurrentDay) BorderStroke(1.dp, currentDayColor) else null,
        contentColor = if (firebasePtoRequestModel != null) selectionColor else contentColorFor(
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
            Text(text = date.dayOfMonth.toString(), color = Color.Black)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun StaticHomeCalendarPreview() {

}

fun com.google.firebase.Timestamp?.toLocalDate(): LocalDate? {
    return this?.toDate()?.toInstant()
        ?.atZone(ZoneId.systemDefault())
        ?.toLocalDate()
}