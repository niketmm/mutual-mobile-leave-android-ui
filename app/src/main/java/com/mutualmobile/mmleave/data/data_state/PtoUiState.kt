package com.mutualmobile.mmleave.data.data_state

import com.mutualmobile.mmleave.data.model.FirebasePtoRequestModel
import com.mutualmobile.mmleave.data.model.PtoRequestDomain
import java.time.LocalDate

data class PtoUiState(
    var allPtoDatesList : List<PtoRequestDomain?>? = emptyList(),
    var allPtoRequestRemoteList : List<FirebasePtoRequestModel?> = emptyList(),
    var localDateList : List<LocalDate> = emptyList()
)