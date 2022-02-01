package com.mutualmobile.mmleave.feature_pto.presentation

sealed class PtoRequestEvents{
    data class Failed(val message : String) : PtoRequestEvents()
    object Success : PtoRequestEvents()
    object Loading : PtoRequestEvents()
}
