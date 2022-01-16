package com.mutualmobile.mmleave.data.ui_event

sealed class SavePtoRequestEvents{
    data class ShowSnackBar(val message : String) : SavePtoRequestEvents()
    object SavedPto : SavePtoRequestEvents()
}
