package com.mutualmobile.mmleave.feature_pto.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.mutualmobile.mmleave.feature_pto.domain.model.PtoRequestDateModel
import com.mutualmobile.mmleave.feature_pto.domain.model.MMUser
import com.mutualmobile.mmleave.feature_notification.domain.model.NotificationModel
import com.mutualmobile.mmleave.feature_pto.domain.model.PtoRequestDomain
import com.mutualmobile.mmleave.feature_pto.data.service.toFirebaseTimestamp
import com.mutualmobile.mmleave.feature_pto.domain.usecase.ApplyPtoUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class ApplyPtoViewModel @Inject constructor(
    private val applyPtoUseCases: ApplyPtoUseCases
) : ViewModel() {

    init {
        getAdminUserList()
    }

    private val _adminListState = mutableStateOf(SearchResultState())
    val adminListState: State<SearchResultState> = _adminListState

    private val _ptoUiStates = mutableStateOf(PtoUiState())
    val ptoUiStates : State<PtoUiState> = _ptoUiStates

    // One time event only
    private val _uiEvents = MutableSharedFlow<SavePtoRequestEvents>()
    val uiEvents = _uiEvents.asSharedFlow()

    private val _userPtoLeftState = MutableStateFlow(0)
    val userPtoLeftState = _userPtoLeftState

    fun onEvents(applyPtoEvent: ApplyPtoEvent){
        when(applyPtoEvent) {

            ApplyPtoEvent.ApplyForPto -> {
                applyPtoRequest()
            }

            is ApplyPtoEvent.AddDescription -> {
                _ptoUiStates.value = ptoUiStates.value.copy(
                    leaveDescriptionText = applyPtoEvent.desc
                )
            }

            is ApplyPtoEvent.SearchUser -> {
                viewModelScope.launch {
                    applyPtoEvent.username.let { query ->
                        applyPtoUseCases.fetchUsersByUsernameUseCase.invoke(query).collect {
                            _adminListState.value = adminListState.value.copy(
                                filteredList = it
                            )
                        }
                    }
                }
            }

            is ApplyPtoEvent.SelectAdmins -> {
                _adminListState.value = adminListState.value.copy(
                    selectedAdminList = applyPtoEvent.selectedAdminList
                )
            }

            is ApplyPtoEvent.SelectPtoDates -> {
                _ptoUiStates.value = ptoUiStates.value.copy(
                    localDateList = applyPtoEvent.selectedPtoDates
                )
            }
        }
    }

    private fun getAdminUserList() {
        viewModelScope.launch {
            applyPtoUseCases.fetchAdminListUseCase.invoke().collect {
                _adminListState.value = adminListState.value.copy(
                    adminList = it
                )
            }
        }
    }

    private fun applyPtoRequest() {
        viewModelScope.launch {
            _ptoUiStates.value.allPtoDatesList?.let {
                applyPtoUseCases.makePtoRequestUseCase.invoke(
                    ptoRequestsList = it,
                    selectedAdminsList = adminListState.value.selectedAdminList.map { mmUser ->
                        mmUser?.email
                    }).collect { events ->
                    when (events) {
                        is PtoRequestEvents.Failed -> {
                            _uiEvents.emit(SavePtoRequestEvents.ShowSnackBar(events.message))
                        }
                        PtoRequestEvents.Success -> {
                            // Success PTO request Done, Save the Notification Data as well and Cache the PTO leaves
                            _uiEvents.emit(SavePtoRequestEvents.SavedPto)
                            // Update the cache and Remote
                            _ptoUiStates.value = ptoUiStates.value.copy(
                                totalLeaveLeft = _ptoUiStates.value.cachedLeaveLeft?.minus(_ptoUiStates.value.localDateList.size)
                            )
                            ptoUiStates.value.totalLeaveLeft?.let { leaves ->
                                setUserPtoLeft(leaveLeft = leaves)
                                updateUserDetailsCall(leaveLeft = leaves)
                            }
                            adminListState.value.selectedAdminList.forEach { admins ->
                                applyPtoUseCases.sendNotificationToAdminUseCase.invoke(
                                    NotificationModel(
                                        datesList = ptoUiStates.value.localDateList.toFirebaseTimeStamp(),
                                        notify_to = admins?.email,
                                        notify_from = FirebaseAuth.getInstance().currentUser?.email,
                                        title = "Hi, this is the request for the PTO's",
                                        notify_type = 0
                                    )
                                )
                            }
                        }
                        PtoRequestEvents.Loading -> {
                            _uiEvents.emit(SavePtoRequestEvents.ShowSnackBar("Processing"))
                        }
                    }
                }
            }
        }
    }

    fun updatePtoList(
        selectedAdmins: List<MMUser?>,
        ptoList: List<LocalDate>,
        email: String?,
        desc: String?
    ) {
        val list = mutableListOf<PtoRequestDomain>()
        ptoList.forEach {
            val obj =
                PtoRequestDomain(
                    email = email,
                    description = desc,
                    date = it,
                    status = PtoRequestDateModel.PtoGraphStatus.APPLIED,
                    adminList = selectedAdmins
                )
            list.add(obj)
        }

        _ptoUiStates.value = ptoUiStates.value.copy(
            allPtoDatesList = list
        )
    }

    fun isValidPtoRequest(
        appliedPtoDates: List<LocalDate>,
        selectedAdmins: List<MMUser?>
    ): Boolean {
        return appliedPtoDates.isNotEmpty() && selectedAdmins.isNotEmpty()
    }

    fun getUserPtoLeft(){
        viewModelScope.launch {
           applyPtoUseCases.getUserPtoLeftUseCase.invoke().collect {
               _ptoUiStates.value = ptoUiStates.value.copy(
                   cachedLeaveLeft = it
               )
                _userPtoLeftState.emit(it)
            }
        }
    }

    private fun updateUserDetailsCall(leaveLeft : Int){
        viewModelScope.launch {
            applyPtoUseCases.updateUserPtoDetailsUseCase.invoke(leaveLeft = leaveLeft)
        }
    }

    private fun setUserPtoLeft(leaveLeft : Int){
        viewModelScope.launch {
                applyPtoUseCases.setUserPtoLeftUseCase.invoke(leaveLeft)
        }
    }

    private fun List<LocalDate>.toFirebaseTimeStamp() : List<Timestamp?>{
        return this.map { localDate ->
            localDate.toFirebaseTimestamp()
        }
    }
}