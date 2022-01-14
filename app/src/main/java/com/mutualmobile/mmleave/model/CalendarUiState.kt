package com.mutualmobile.mmleave.model

import java.time.LocalDate

data class CalendarUiState(
    var allPtoDatesList : List<CalendarPtoRequest> = emptyList(),
    var localDateList : List<LocalDate?> = emptyList()
)