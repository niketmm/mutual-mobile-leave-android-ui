package com.mutualmobile.mmleave.feature_availed.presentation

import com.mutualmobile.mmleave.data.model.FirebasePtoRequestModel

sealed class AvailedPtoEvent {
    data class ShowPtoDetailClickEvent(val firebasePtoRequestModel: FirebasePtoRequestModel) :
        AvailedPtoEvent()
}