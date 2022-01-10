package com.mutualmobile.mmleave.screens.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.github.boguszpawlowski.composecalendar.StaticCalendar
import io.github.boguszpawlowski.composecalendar.rememberCalendarState


@Composable
fun StaticHomeCalendar() {
    StaticCalendar(
        modifier = Modifier.fillMaxWidth()
            .wrapContentHeight(),
        calendarState = rememberCalendarState()
    )
}

@Preview(showBackground = true)
@Composable
fun StaticHomeCalendarPreview() {
    StaticHomeCalendar()
}