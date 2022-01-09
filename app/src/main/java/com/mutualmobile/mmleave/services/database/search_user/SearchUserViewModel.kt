package com.mutualmobile.mmleave.services.database.search_user

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutualmobile.mmleave.screens.search.SearchResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchUserViewModel @Inject constructor(
    private val searchUserService: SearchUserService
): ViewModel() {

    init {
        getAdminUserList()
    }

    private val _adminListState = mutableStateOf(SearchResultState())
    val adminListState: State<SearchResultState> = _adminListState

    private fun getAllUserList() {
        viewModelScope.launch {
            searchUserService.fetchAllUserList().collect {
                _adminListState.value = adminListState.value.copy(
                    userList = it
                )
            }
        }
    }

    private fun getAdminUserList() {
        viewModelScope.launch {
            searchUserService.fetchAdminList().collect {
                _adminListState.value = adminListState.value.copy(
                    adminList = it
                )
            }
        }
    }

    fun getFilteredAdminUserList(query: String?) {
        viewModelScope.launch {
            query?.let { query ->
                searchUserService.fetchUsersByUsername(query).collect {
                    _adminListState.value = adminListState.value.copy(
                        filteredList = it
                    )
                }
            }
        }
    }
}