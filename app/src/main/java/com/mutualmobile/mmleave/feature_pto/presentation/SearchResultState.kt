package com.mutualmobile.mmleave.feature_pto.presentation

import com.mutualmobile.mmleave.feature_pto.domain.model.MMUser

data class SearchResultState(
    val userList : List<MMUser?> = emptyList(),
    val adminList : List<MMUser?> = emptyList(),
    val filteredList : List<MMUser?> = emptyList(),
    val selectedAdminList : List<MMUser?> = emptyList()
)