package com.mutualmobile.mmleave.screens.pto.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutualmobile.mmleave.firestore.PtoRequestDateModel
import com.mutualmobile.mmleave.firestore.SetGetPtoRequests
import com.mutualmobile.mmleave.model.PtoUiState
import com.mutualmobile.mmleave.screens.search.SearchResultState
import com.mutualmobile.mmleave.services.database.ptorequest.PtoRequestServiceImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class PtoRequestViewModel @Inject constructor(private val ptoRequestService: PtoRequestServiceImpl) :
    ViewModel() {

    init {
        getAdminUserList()
    }

    private val _allPtoSelectedList = mutableStateOf(PtoUiState())
    val allPtoSelectedList: State<PtoUiState> = _allPtoSelectedList

    private val _adminListState = mutableStateOf(SearchResultState())
    val adminListState: State<SearchResultState> = _adminListState

    var ptoRequestStatus = mutableStateOf(true)

    fun updatePtoList(ptoList: List<LocalDate>,email: String?,desc : String?) {
        val list = mutableListOf<SetGetPtoRequests>()
        ptoList.forEach {
            val obj =
                SetGetPtoRequests(email = email, description = desc, date = it, status = PtoRequestDateModel.PtoGraphStatus.APPLIED)
            list.add(obj)
        }

        _allPtoSelectedList.value = allPtoSelectedList.value.copy(
            allPtoDatesList = list
        )
    }

    // Todo : Make sure User will get updated and get notified that Application has been sent
    fun applyPtoRequest(
        email: String,
        leaveDescriptionText: String
    ) {
        viewModelScope.launch {
            allPtoSelectedList.value.allPtoDatesList?.let {
                ptoRequestService.makePtoRequest(
                    it
                )
            }
        }
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