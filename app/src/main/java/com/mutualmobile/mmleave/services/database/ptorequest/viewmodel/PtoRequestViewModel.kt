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

    var ptoRequestState = mutableStateOf(PtoProperties())
        private set

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
}