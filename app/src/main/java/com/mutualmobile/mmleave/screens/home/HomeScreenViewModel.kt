package com.mutualmobile.mmleave.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.mutualmobile.mmleave.data.model.FirebasePtoRequestModel
import com.mutualmobile.mmleave.data.data_state.CalendarUiState
import com.mutualmobile.mmleave.services.database.availed.AvailedPtoServiceImpl
import com.mutualmobile.mmleave.data.data_store.StoreUserInfo
import com.mutualmobile.mmleave.data.model.DisplayDateModel
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
    private val availedPtoServiceImpl: AvailedPtoServiceImpl,
    private val storeUserInfo: StoreUserInfo
) : ViewModel() {

    private val _userPtoLeftState = MutableStateFlow(0)
    val userPtoLeftState = _userPtoLeftState

    init {
        getLocalDateList()
        displayDate()
        fetchAllHolidays()
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

    fun getLatestPtoRequest() {
        viewModelScope.launch {
            availedPtoServiceImpl.fetchLatestPtoRequests().collect {
                Log.d("LatestPtoRequest", "getLatestPtoRequest: $it")
                _allPtoSelectedList.value = allPtoSelectedList.value.copy(
                    latestPtoRequest = it
                )
            }
        }
    }

    fun getUserPtoLeft() {
        viewModelScope.launch {
            storeUserInfo.getUserTotalPto.collect {
                _userPtoLeftState.emit(it)
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

    fun isUserAdmin() {
        viewModelScope.launch {
            storeUserInfo.getIsUserAdminState.collect { prefBoolean ->
                prefBoolean?.let { it1 -> _isUserAdminState.emit(it1) }
            }
        }
    }

    fun fetchAndCacheUserData() {
        viewModelScope.launch {
            calendarDataService.fetchUserDetails(
                FirebaseModule.currentUser
            ).collect { user ->
                user?.let {
                    // Caching the Response Synced With the Data base
                    storeUserInfo.setUserTotalPto(totalPtoLeavesLeft = it.leaveLeft ?: 0)
                    storeUserInfo.setIsUserAdminState(isAdmin = it.userType == 1)
                    storeUserInfo.setUserAuthenticateState(true)
                }
            }
        }
    }

    private fun fetchAllHolidays(){
        viewModelScope.launch {
            calendarDataService.fetchHolidays().collect { holidayList ->
                _allPtoSelectedList.value = allPtoSelectedList.value.copy(
                    allHolidayStatusList = holidayList
                )
                _allPtoSelectedList.value = allPtoSelectedList.value.copy(
                    holidayDateList = holidayList.map {
                        it.date.toLocalDate()
                    }
                )
            }
        }
    }

    /**
     * `setHolidayAndSelectedLocalDateList()` method concatenate two list and
     *  set the resultant list to `holidayAndSelectedLocalDateList`
     */
    fun setHolidayAndSelectedLocalDateList(){
        viewModelScope.launch {
            _allPtoSelectedList.value = allPtoSelectedList.value.copy(
                holidayAndSelectedLocalDateList = concatenate(
                    _allPtoSelectedList.value.holidayDateList,
                    _allPtoSelectedList.value.localDateList
                )
            )
        }
    }

    /**
     * `setHolidayAndPtoRequestedStatusDateList()` method concatenate two list and
     *  set the resultant list to `holidayAndPtoRequestedStatusDateList`
     */
    fun setHolidayAndPtoRequestedStatusDateList(){
        viewModelScope.launch {
            val ptoFetchedStatus = _allPtoSelectedList.value.allPtoDatesListModel.map { firebaseFetchedPtoItem ->
                DisplayDateModel(
                    date = firebaseFetchedPtoItem.date,
                    ptoStatus = firebaseFetchedPtoItem.ptoStatus
                )
            }

            _allPtoSelectedList.value = allPtoSelectedList.value.copy(
                holidayAndPtoRequestedStatusDateList = concatenate(
                    _allPtoSelectedList.value.allHolidayStatusList,
                    ptoFetchedStatus
                )
            )
        }
    }

    fun logoutUser() {
        // Reset the Cache
        viewModelScope.launch {
            storeUserInfo.setIsUserAdminState(false)
            storeUserInfo.setUserTotalPto(0)
            storeUserInfo.setUserAuthenticateState(false)
            FirebaseAuth.getInstance().signOut()
            // Todo Add Google Sign out
        }
    }

    fun <T> concatenate(vararg lists: List<T>): List<T> {
        return listOf(*lists).flatten()
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