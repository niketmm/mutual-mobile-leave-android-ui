package com.mutualmobile.mmleave.data.data_state

import com.mutualmobile.mmleave.data.model.SetGetPtoRequests
import java.time.LocalDate

data class PtoUiState(
    var allPtoDatesList : List<SetGetPtoRequests?>? = emptyList(),
    var localDateList : List<LocalDate> = emptyList()
)