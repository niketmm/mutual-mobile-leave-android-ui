package com.mutualmobile.mmleave.data.data_state

import com.mutualmobile.mmleave.data.model.MMUser

data class SearchResultState(
    val userList : List<MMUser?> = emptyList(),
    val adminList : List<MMUser?> = emptyList(),
    val filteredList : List<MMUser?> = emptyList(),
    val selectedAdminList : List<MMUser?> = emptyList()
)