package com.mutualmobile.mmleave.feature_home.presentation

import com.mutualmobile.mmleave.data.model.FirebasePtoRequestModel

data class HomeDataState(
    var latestPtoRequest : FirebasePtoRequestModel? = null,
    var totalPtoLeftCached : Int? = 0,
    var maxPtoAvailable : Int? = 0,
    val isCalendarExpanded : Boolean? = false,
    val isDescriptionTextExpanded : Boolean? = false
)