package com.mutualmobile.mmleave.feature_home.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.mutualmobile.mmleave.common_ui.components.toLocalDate
import com.mutualmobile.mmleave.data.model.FirebasePtoRequestModel
import com.mutualmobile.mmleave.data.model.DisplayDateModel
import com.mutualmobile.mmleave.di.FirebaseModule
import com.mutualmobile.mmleave.feature_home.domain.usecases.HomeUseCases
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
    private val homeUseCases: HomeUseCases
) : ViewModel() {

    init {
        getLocalDateList()
        displayDate()
        fetchAllHolidays()
    }

    private var _isUserAdminState = MutableStateFlow(false)
    val isUserAdminState = _isUserAdminState

    private val _allPtoSelectedList = MutableStateFlow(CalendarDatesState())
    val allPtoSelectedList: StateFlow<CalendarDatesState> = _allPtoSelectedList

    private var _homeDetailsState = mutableStateOf(HomeDataState())
    val homeDetailsState = _homeDetailsState

    fun onEvent(events : HomeUiEvent){
        when(events){
            HomeUiEvent.LogoutUser -> {
                viewModelScope.launch {
                    homeUseCases.logoutUseCase.invoke()
                }
            }
            HomeUiEvent.ToggleCalendarSection -> {
                _homeDetailsState.value = homeDetailsState.value.copy(
                    isCalendarExpanded = homeDetailsState.value.isCalendarExpanded?.not()
                )
            }
            HomeUiEvent.ToggleExpandableText -> {
                _homeDetailsState.value = homeDetailsState.value.copy(
                    isDescriptionTextExpanded = homeDetailsState.value.isDescriptionTextExpanded?.not()
                )
            }
        }
    }

    private fun displayDate() {
        viewModelScope.launch {
            homeUseCases.getUserDatesListUseCase.invoke().collect {
                _allPtoSelectedList.value = allPtoSelectedList.value.copy(
                    allPtoDatesListModel = it.toNonNullList()
                )
            }
        }
    }

    private fun getLocalDateList() {
        viewModelScope.launch {
            homeUseCases.getUserDatesListUseCase.invoke().collect {
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
            homeUseCases.getLatestPtoRequestUseCase.invoke().collect {
                _homeDetailsState.value = homeDetailsState.value.copy(
                    latestPtoRequest = it
                )
            }
        }
    }

    fun getUserPtoLeft() {
        viewModelScope.launch {
           homeUseCases.getTotalCachedPtoUseCase.invoke().collect {
               _homeDetailsState.value = homeDetailsState.value.copy(
                   totalPtoLeftCached = it
               )
           }
        }
    }


    fun isUserAdmin() {
        viewModelScope.launch {
            homeUseCases.getIsUserAdminUseCase.invoke().collect { isUserAdmin ->
                isUserAdmin?.let { value -> _isUserAdminState.emit(value) }
            }
        }
    }
    fun fetchAndCacheUserData() {
        viewModelScope.launch {
            homeUseCases.getUserDetailsUseCase.invoke(FirebaseModule.currentUser.toString())
                .collect { user ->
                user?.let {
                    // Caching the Response Synced With the Data base
                    homeUseCases.syncCacheUseCase.invoke(
                        isUserAuthenticated = true,
                        totalPtoRequestLeft = it.leaveLeft ?: 0,
                        isUserAdmin = it.userType == 1
                    )
                }
            }
        }
    }

    private fun fetchAllHolidays(){
        viewModelScope.launch {
            homeUseCases.getHolidaysUseCase.invoke().collect { holidayList ->
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

    private fun <T> concatenate(vararg lists: List<T>): List<T> {
        return listOf(*lists).flatten()
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
}