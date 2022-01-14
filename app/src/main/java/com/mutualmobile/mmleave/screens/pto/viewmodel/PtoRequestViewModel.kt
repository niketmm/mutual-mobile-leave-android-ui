package com.mutualmobile.mmleave.screens.pto.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutualmobile.mmleave.data.model.PtoRequestDateModel
import com.mutualmobile.mmleave.data.data_state.PtoUiState
import com.mutualmobile.mmleave.data.model.SetGetPtoRequests
import com.mutualmobile.mmleave.services.database.ptorequest.PtoRequestServiceImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class PtoRequestViewModel @Inject constructor(private val ptoRequestService: PtoRequestServiceImpl) :
    ViewModel() {

    private val _allPtoSelectedList = mutableStateOf(PtoUiState())
    val allPtoSelectedList: State<PtoUiState> = _allPtoSelectedList

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
}