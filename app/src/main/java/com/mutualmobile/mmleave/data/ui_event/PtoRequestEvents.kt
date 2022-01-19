package com.mutualmobile.mmleave.data.ui_event

import com.google.firebase.inject.Deferred
import com.mutualmobile.mmleave.data.model.FirebasePtoRequestModel

sealed class PtoRequestEvents{
    data class Failed(val message : String) : PtoRequestEvents()
    object Success : PtoRequestEvents()
    object Loading : PtoRequestEvents()
}
