package com.mutualmobile.mmleave.feature_home.presentation

import com.mutualmobile.mmleave.feature_home.domain.model.DisplayDateModel
import com.mutualmobile.mmleave.feature_pto.domain.model.FirebasePtoRequestModel
import java.time.LocalDate

data class CalendarDatesState(
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