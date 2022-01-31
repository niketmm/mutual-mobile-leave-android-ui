package com.mutualmobile.mmleave.feature_availed.presentation

import com.mutualmobile.mmleave.data.model.FirebasePtoRequestModel

data class PtoAvailedState(
    val allPtoAvailedList : List<FirebasePtoRequestModel?> = emptyList(),
    val totalPtoLeft : Int = 0
)