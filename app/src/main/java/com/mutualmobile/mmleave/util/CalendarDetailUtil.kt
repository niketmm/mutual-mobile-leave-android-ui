package com.mutualmobile.mmleave.util

import androidx.compose.ui.graphics.Color

data class CalendarDetailUtil(
    val boxColour : Color,
    val title : String
)

object CalendarDetailList {
    val firstRowList = listOf<CalendarDetailUtil>(
        CalendarDetailUtil(Color.Green,"Applied"),
        CalendarDetailUtil(Color.Cyan,"Approved"),
        CalendarDetailUtil(Color.Yellow,"Holiday")
    )

    val secondRowList = listOf<CalendarDetailUtil>(
        CalendarDetailUtil(Color.Red,"Rejected"),
        CalendarDetailUtil(Color.Blue,"Pending"),
        CalendarDetailUtil(Color.Magenta,"Updated")
    )
}