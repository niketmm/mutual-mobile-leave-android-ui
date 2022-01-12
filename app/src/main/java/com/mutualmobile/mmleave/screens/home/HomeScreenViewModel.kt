package com.mutualmobile.mmleave.screens.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.type.DateTime
import com.mutualmobile.mmleave.model.CalendarPtoRequest
import com.mutualmobile.mmleave.model.CalendarUiState
import com.mutualmobile.mmleave.model.PtoUiState
import com.mutualmobile.mmleave.services.database.home.CalendarDataServiceImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.sql.Date
import java.sql.Timestamp
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val calendarDataService: CalendarDataServiceImpl
) : ViewModel() {

    init {
        getLocalDateList()
        getLocalDates()
    }

    private val _allPtoSelectedList = mutableStateOf(CalendarUiState())
    val allPtoSelectedList: State<CalendarUiState> = _allPtoSelectedList

    private val _state = MutableStateFlow<List<LocalDate>>(emptyList())
    val state: StateFlow<List<LocalDate>>
        get() = _state

    suspend fun displayDate() {
        viewModelScope.launch {
            calendarDataService.fetchUserDatesList().collect {
                _allPtoSelectedList.value = allPtoSelectedList.value.copy(
                    allPtoDatesList = it.toNonNullList()
                )
            }
        }
    }

    private fun getLocalDateList() {
        viewModelScope.launch {
            calendarDataService.fetchUserDatesList().collect {
                _allPtoSelectedList.value = it.map { pto ->
                    pto?.date
                }.let { it1 ->
                    allPtoSelectedList.value.copy(
                        localDateList = it1.toLocalTime()
                    )
                }
            }
        }
    }

    private fun getLocalDates() = viewModelScope.launch {
        calendarDataService.fetchUserDatesList().collect { firebaseObjList ->
            _state.value = firebaseObjList.map {
                val localDate  = it!!.date!!.toDate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()

                Log.d("TAG", "getLocalDates: $localDate")
                localDate
            }
        }
    }

    private fun List<CalendarPtoRequest?>.toNonNullList(): List<CalendarPtoRequest> {
        return this.map {
            it!!
        }
    }

    private fun List<com.google.firebase.Timestamp?>.toLocalTime(): List<LocalDate?> {
        return this.map { timestamp ->
            timestamp?.toDate()?.toInstant()
                ?.atZone(ZoneId.systemDefault())
                ?.toLocalDate()
        }
    }
}