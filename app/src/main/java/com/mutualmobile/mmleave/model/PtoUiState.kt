package com.mutualmobile.mmleave.model

import com.mutualmobile.mmleave.firestore.SetGetPtoRequests
import com.mutualmobile.mmleave.model.CalendarPtoRequest
import java.time.LocalDate

data class PtoUiState(
    var allPtoDatesList : List<SetGetPtoRequests?>? = emptyList(),
    var localDateList : List<LocalDate?> = emptyList()
)