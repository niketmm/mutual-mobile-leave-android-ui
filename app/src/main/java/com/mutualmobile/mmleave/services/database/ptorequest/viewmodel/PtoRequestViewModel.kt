package com.mutualmobile.mmleave.services.database.ptorequest.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutualmobile.mmleave.firestore.PtoProperties
import com.mutualmobile.mmleave.services.database.ptorequest.PtoRequestService
import com.mutualmobile.mmleave.services.database.ptorequest.PtoRequestServiceImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PtoRequestViewModel @Inject constructor(private val ptoRequestService: PtoRequestServiceImpl) :
  ViewModel() {

  var ptoRequestState = mutableStateOf(PtoProperties())
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
}