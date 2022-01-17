package com.mutualmobile.mmleave.screens.pto.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.mutualmobile.mmleave.data.model.PtoRequestDateModel
import com.mutualmobile.mmleave.data.data_state.PtoUiState
import com.mutualmobile.mmleave.data.model.Admins
import com.mutualmobile.mmleave.data.model.MMUser
import com.mutualmobile.mmleave.data.model.NotificationModel
import com.mutualmobile.mmleave.data.model.PtoRequestDomain
import com.mutualmobile.mmleave.data.ui_event.PtoRequestEvents
import com.mutualmobile.mmleave.data.ui_event.SavePtoRequestEvents
import com.mutualmobile.mmleave.services.database.availed.AvailedPtoServiceImpl
import com.mutualmobile.mmleave.services.database.notification.NotificationRequesterImpl
import com.mutualmobile.mmleave.services.database.ptorequest.PtoRequestServiceImpl
import com.mutualmobile.mmleave.services.database.ptorequest.toFirebaseTimestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class PtoRequestViewModel @Inject constructor(
    private val ptoRequestService: PtoRequestServiceImpl,
    private val availedPtoServiceImpl: AvailedPtoServiceImpl,
    private val notificationRequesterImpl: NotificationRequesterImpl
) : ViewModel() {

    private val _allPtoSelectedList = mutableStateOf(PtoUiState())
    val allPtoSelectedList: State<PtoUiState> = _allPtoSelectedList

    // One time event only
    private val _uiEvents = MutableSharedFlow<SavePtoRequestEvents>()
    val uiEvents = _uiEvents.asSharedFlow()

    fun getAllRemotePtoRequest(){
        viewModelScope.launch {
            availedPtoServiceImpl.fetchAllPtoRequests().collect { remotePtoRequestList ->
                _allPtoSelectedList.value = allPtoSelectedList.value.copy(
                    allPtoRequestRemoteList = remotePtoRequestList
                )
            }
        }
    }

    fun applyPtoRequest(selectedAdmins: List<MMUser?>) {
        viewModelScope.launch {
            _allPtoSelectedList.value.allPtoDatesList?.let {
                ptoRequestService.makePtoRequest(
                    ptoRequests = it,
                    selectedAdmins = selectedAdmins.map { mmUser ->
                        mmUser?.email
                    }
                ).collect { events ->
                    when (events) {
                        is PtoRequestEvents.Failed -> {
                            _uiEvents.emit(SavePtoRequestEvents.ShowSnackBar(events.message))
                        }
                        PtoRequestEvents.Success -> {
                            // Success PTO request Done, Save the Notification Data as well
                            _uiEvents.emit(SavePtoRequestEvents.SavedPto)
                            selectedAdmins.forEach { admins ->
                                notificationRequesterImpl.saveNotification(
                                    NotificationModel(
                                        datesList = allPtoSelectedList.value.localDateList.toFirebaseTimeStamp(),
                                        notify_to = admins?.email,
                                        notify_from = FirebaseAuth.getInstance().currentUser?.email,
                                        title = "Hi, this is the request for the PTO's",
                                        notify_type = 1
                                    )
                                )
                            }
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

        _allPtoSelectedList.value = allPtoSelectedList.value.copy(
            allPtoDatesList = list
        )
    }

    fun isValidPtoRequest(
        appliedPtoDates: List<LocalDate>,
        selectedAdmins: List<MMUser?>
    ): Boolean {
        return appliedPtoDates.isNotEmpty() && selectedAdmins.isNotEmpty()
    }

    fun List<LocalDate?>.toFirebaseTimeStamp() : List<Timestamp?>{
        return this.map { localDate ->
            localDate?.toFirebaseTimestamp()
        }
    }
}