package com.mutualmobile.mmleave.data.data_state

import com.mutualmobile.mmleave.data.model.CalendarPtoRequest
import java.time.LocalDate

data class CalendarUiState(
    var allPtoDatesList : List<CalendarPtoRequest> = emptyList(),
    var localDateList : List<LocalDate?> = emptyList()
)