package com.mutualmobile.mmleave.feature_home.presentation

sealed class HomeUiEvent{
    object ToggleCalendarSection : HomeUiEvent()
    object LogoutUser : HomeUiEvent()
    object ToggleExpandableText : HomeUiEvent()
}
