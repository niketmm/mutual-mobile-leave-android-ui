package com.mutualmobile.mmleave.screens.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutualmobile.mmleave.services.database.ptorequest.FirebasePtoRequest
import com.mutualmobile.mmleave.services.database.ptorequest.PtoRequestServiceImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val ptoRequestService: PtoRequestServiceImpl) :
    ViewModel() {
  var ptoListRequestState = mutableStateOf<List<FirebasePtoRequest>>(mutableListOf())
    private set

  init {
    getAllPtosList()
  }
  fun getAllPtosList() {
    viewModelScope.launch {
      ptoListRequestState.value = ptoRequestService.getAllPtoRequestsList()
    }
  }
}