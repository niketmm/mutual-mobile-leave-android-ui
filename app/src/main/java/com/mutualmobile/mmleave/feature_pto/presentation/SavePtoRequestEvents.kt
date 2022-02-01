package com.mutualmobile.mmleave.feature_pto.presentation

sealed class SavePtoRequestEvents{
    data class ShowSnackBar(val message : String) : SavePtoRequestEvents()
    object SavedPto : SavePtoRequestEvents()
}
