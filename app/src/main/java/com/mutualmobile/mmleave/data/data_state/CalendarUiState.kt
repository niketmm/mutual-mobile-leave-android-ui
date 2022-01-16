package com.mutualmobile.mmleave.data.data_state

import com.mutualmobile.mmleave.data.model.FirebasePtoRequestModel
import java.time.LocalDate

data class CalendarUiState(
    var allPtoDatesListModel : List<FirebasePtoRequestModel> = emptyList(),
    var localDateList : List<LocalDate?> = emptyList()
)