package com.mutualmobile.mmleave.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutualmobile.mmleave.data.model.FirebasePtoRequestModel
import com.mutualmobile.mmleave.data.data_state.CalendarUiState
import com.mutualmobile.mmleave.data.data_store.StoreUserInfo
import com.mutualmobile.mmleave.data.model.MMUser
import com.mutualmobile.mmleave.di.FirebaseModule
import com.mutualmobile.mmleave.services.database.home.CalendarDataServiceImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val calendarDataService: CalendarDataServiceImpl,
    private val storeUserInfo: StoreUserInfo
) : ViewModel() {

    init {
//        testingFirebaseQueries()
//        isUserAdmin()
        getLocalDateList()
        displayDate()
    }

    private val _isUserAdminState = MutableStateFlow(false)
    val isUserAdminState = _isUserAdminState

    private val _allPtoSelectedList = MutableStateFlow(CalendarUiState())
    val allPtoSelectedList: StateFlow<CalendarUiState> = _allPtoSelectedList

    private fun displayDate() {
        viewModelScope.launch {
            calendarDataService.fetchUserDatesList().collect {
                _allPtoSelectedList.value = allPtoSelectedList.value.copy(
                    allPtoDatesListModel = it.toNonNullList()
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

    private fun List<FirebasePtoRequestModel?>.toNonNullList(): List<FirebasePtoRequestModel> {
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

    private fun isUserAdmin() {
        viewModelScope.launch {
            storeUserInfo.getIsUserAdminState.collect { prefBoolean ->
                prefBoolean?.let { it1 -> _isUserAdminState.emit(it1) }
            }
        }
    }

//    private suspend fun testingFirebaseQueries(email : String? = "niket.jain@mutualmobile.com") {
//        viewModelScope.launch {
//            val isAdmin = FirebaseModule.provideFirebaseUserCollectionReference()
//                .whereEqualTo("email", email)
//                .addSnapshotListener { users, error ->
//                    users?.toObjects(MMUser::class.java)?.let {
//                        Log.d("TestingFirebase", "testingFirebaseQueries: $it")
//                    }
//                }
//        }
//    }
}