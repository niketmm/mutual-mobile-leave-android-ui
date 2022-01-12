package com.mutualmobile.mmleave.screens.pto

import com.mutualmobile.mmleave.firestore.SetGetPtoRequests

data class PtoUiState(
    var allPtoDatesList : List<SetGetPtoRequests>? = emptyList()
)