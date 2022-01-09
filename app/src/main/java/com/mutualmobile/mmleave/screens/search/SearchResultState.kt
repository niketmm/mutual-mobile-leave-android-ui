package com.mutualmobile.mmleave.screens.search

import com.mutualmobile.mmleave.data.model.MMUser

data class SearchResultState(
    val userList : List<MMUser?> = emptyList(),
    val adminList : List<MMUser?> = emptyList(),
    val filteredList : List<MMUser?> = emptyList()
)