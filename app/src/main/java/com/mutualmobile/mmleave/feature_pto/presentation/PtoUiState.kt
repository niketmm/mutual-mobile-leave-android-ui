package com.mutualmobile.mmleave.feature_pto.presentation

import com.mutualmobile.mmleave.feature_pto.domain.model.FirebasePtoRequestModel
import com.mutualmobile.mmleave.feature_pto.domain.model.PtoRequestDomain
import java.time.LocalDate

data class PtoUiState(
    var allPtoDatesList : List<PtoRequestDomain?>? = emptyList(),
    var allPtoRequestRemoteList : List<FirebasePtoRequestModel?> = emptyList(),
    var localDateList : List<LocalDate> = emptyList(),
    var cachedLeaveLeft : Int? = 0,
    var totalLeaveLeft : Int? = 0,
    var leaveDescriptionText : String? = null
)