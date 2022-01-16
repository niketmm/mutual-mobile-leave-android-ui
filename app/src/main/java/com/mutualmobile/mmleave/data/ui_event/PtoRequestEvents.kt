package com.mutualmobile.mmleave.data.ui_event

sealed class PtoRequestEvents{
    data class Failed(val message : String) : PtoRequestEvents()
    object Success : PtoRequestEvents()
}
