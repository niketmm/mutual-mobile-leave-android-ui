package com.mutualmobile.mmleave.feature_availed.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutualmobile.mmleave.feature_availed.domain.usecases.PtoAvailedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PtoAvailedViewModel @Inject constructor(
    private val ptoAvailedUseCase: PtoAvailedUseCase
) : ViewModel() {

    private var _ptoAvailedState = mutableStateOf(PtoAvailedState())
    val ptoAvailedState = _ptoAvailedState

    private var _ptoAvailedUiEvents = MutableSharedFlow<PtoAvailedUiEvents>()
    val ptoAvailedUiEvents = _ptoAvailedUiEvents.asSharedFlow()

    init {
        getAllPtoAvailedList()
        getTotalPtoLeft()
    }

    fun onEvents(event: AvailedPtoEvent){
        when(event){
            is AvailedPtoEvent.ShowPtoDetailClickEvent -> {
                // Load the Data of a Specific Item and change the Screen
                // Better show a Bottom Sheet
            }
        }
    }

    private fun getAllPtoAvailedList() {
        viewModelScope.launch {
            ptoAvailedUseCase.getAllPtoAvailedUseCase.invoke().collect {
                _ptoAvailedState.value = ptoAvailedState.value.copy(
                    allPtoAvailedList = it
                )
            }
        }
    }

    private fun getTotalPtoLeft() {
        viewModelScope.launch {
            ptoAvailedUseCase.getTotalPtoLeftUseCase.invoke().collect {
                _ptoAvailedState.value = ptoAvailedState.value.copy(
                    totalPtoLeft = it
                )
            }
        }
    }
}