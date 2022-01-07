package com.mutualmobile.mmleave.screens.pto.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutualmobile.mmleave.firestore.PtoRequest
import com.mutualmobile.mmleave.services.database.ptorequest.PtoRequestServiceImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class PtoRequestViewModel @Inject constructor(private val ptoRequestService: PtoRequestServiceImpl) :
    ViewModel() {

  var ptoRequestState = mutableStateOf<PtoRequest>(PtoRequest())
    private set

  var ptoRequestStatus = mutableStateOf(true)

  fun applyPtoRequest(
    email: String,
    leaveDescriptionText: String
  ) {
    viewModelScope.launch {
      ptoRequestStatus.value = ptoRequestService.makePtoRequest(
          ptoRequestState.value.copy(
              email = email,
              description = leaveDescriptionText
          )
      )
    }
  }

  fun updatePtoList(ptoList: List<LocalDate>) {
    ptoRequestState.value = ptoRequestState.value.copy(ptoList = ptoList)
  }
}

//disable apply pto button when no item in pto list