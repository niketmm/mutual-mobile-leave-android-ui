package com.mutualmobile.mmleave.services.database.ptorequest.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutualmobile.mmleave.data.model.MMUser
import com.mutualmobile.mmleave.firestore.PtoProperties
import com.mutualmobile.mmleave.screens.search.SearchResultState
import com.mutualmobile.mmleave.services.database.ptorequest.PtoRequestServiceImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class PtoRequestViewModel @Inject constructor(private val ptoRequestService: PtoRequestServiceImpl) :
    ViewModel() {

    init {
        getAdminUserList()
    }

    var ptoRequestState = mutableStateOf(PtoProperties())
        private set

    private val _adminListState = mutableStateOf(SearchResultState())
    val adminListState: State<SearchResultState> = _adminListState

    // Todo : Make sure User will get updated and get notified that Application has been sent
    fun applyPtoRequest(
        email: String,
        leaveDescriptionText: String
    ) {
        viewModelScope.launch {
            ptoRequestService.makePtoRequest(
                ptoRequestState.value.copy(
                    email = email,
                    description = leaveDescriptionText
                )
            )
        }
    }

    fun updateDateTo(time: Date) {
        ptoRequestState.value = ptoRequestState.value.copy(dateTo = time)
    }

    fun updateDateFrom(date: Date) {
        ptoRequestState.value = ptoRequestState.value.copy(dateFrom = date)
    }

    private fun getAdminUserList() {
        viewModelScope.launch {
            ptoRequestService.fetchAdminList().collect {
                _adminListState.value = adminListState.value.copy(
                    adminList = it
                )
            }
        }
    }

    fun getFilteredAdminUserList(query: String?) {
        viewModelScope.launch {
            query?.let { query ->
                ptoRequestService.fetchUsersByUsername(query).collect {
                    _adminListState.value = adminListState.value.copy(
                        filteredAdminList = it
                    )
                }
            }
        }
    }
}