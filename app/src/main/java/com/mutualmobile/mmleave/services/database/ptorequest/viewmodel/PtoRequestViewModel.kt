package com.mutualmobile.mmleave.services.database.ptorequest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutualmobile.mmleave.firestore.PtoProperties
import com.mutualmobile.mmleave.services.database.ptorequest.PtoRequestService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PtoRequestViewModel @Inject constructor(private val ptoRequestService: PtoRequestService) :
    ViewModel() {

  fun applyPtoRequest(
    ptoProperties: PtoProperties
  ) {
    viewModelScope.launch {
      ptoRequestService.makePtoRequest(ptoProperties)
    }
  }
}