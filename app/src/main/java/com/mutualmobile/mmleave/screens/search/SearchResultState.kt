package com.mutualmobile.mmleave.screens.search

import com.mutualmobile.mmleave.data.model.MMUser

data class SearchResultState(
    val adminList : List<MMUser?> = emptyList(),
    val filteredAdminList : List<MMUser?> = emptyList()
)