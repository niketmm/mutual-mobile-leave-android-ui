package com.mutualmobile.mmleave.feature_availed.presentation

sealed class PtoAvailedUiEvents {
    data class ShowSnackBar(val message : String) : PtoAvailedUiEvents()
}