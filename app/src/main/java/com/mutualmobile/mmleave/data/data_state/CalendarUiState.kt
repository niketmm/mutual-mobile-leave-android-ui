package com.mutualmobile.mmleave.data.data_state

import com.mutualmobile.mmleave.data.model.DisplayDateModel
import com.mutualmobile.mmleave.data.model.FirebasePtoRequestModel
import java.time.LocalDate

data class CalendarUiState(
    var latestPtoRequest : FirebasePtoRequestModel? = null,
    // FOR PTO Fetching
    var allPtoDatesListModel : List<FirebasePtoRequestModel> = emptyList(),
    var localDateList : List<LocalDate?> = emptyList(),
    // FOR HOLIDAY fetching
    val holidayDateList : List<LocalDate?> = emptyList(),
    val allHolidayStatusList : List<DisplayDateModel?> = emptyList(),

    // Resultant of Merge of Both the List
    val holidayAndSelectedLocalDateList : List<LocalDate?> = emptyList(),
    val holidayAndPtoRequestedStatusDateList : List<DisplayDateModel?> = emptyList()
)