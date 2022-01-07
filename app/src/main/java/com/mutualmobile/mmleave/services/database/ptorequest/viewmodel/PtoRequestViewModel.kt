package com.mutualmobile.mmleave.services.database.ptorequest.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutualmobile.mmleave.firestore.PtoRequest
import com.mutualmobile.mmleave.services.database.ptorequest.PtoRequestServiceImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PtoRequestViewModel @Inject constructor(private val ptoRequestService: PtoRequestServiceImpl) :
  ViewModel() {

  var ptoRequestState = mutableStateOf(PtoRequest())
    private set

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