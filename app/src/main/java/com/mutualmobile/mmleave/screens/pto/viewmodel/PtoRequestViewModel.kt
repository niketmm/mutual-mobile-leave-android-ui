package com.mutualmobile.mmleave.screens.pto.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutualmobile.mmleave.data.model.PtoRequestDateModel
import com.mutualmobile.mmleave.data.data_state.PtoUiState
import com.mutualmobile.mmleave.data.model.Admins
import com.mutualmobile.mmleave.data.model.MMUser
import com.mutualmobile.mmleave.data.model.SetGetPtoRequests
import com.mutualmobile.mmleave.services.database.ptorequest.PtoRequestServiceImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class PtoRequestViewModel @Inject constructor(private val ptoRequestService: PtoRequestServiceImpl) :
    ViewModel() {

    private val _allPtoSelectedList = mutableStateOf(PtoUiState())
    val allPtoSelectedList: State<PtoUiState> = _allPtoSelectedList

    fun applyPtoRequest(
        selectedAdmins: List<MMUser?>
    ) {
        viewModelScope.launch {
            _allPtoSelectedList.value.allPtoDatesList?.let {
                ptoRequestService.makePtoRequest(
                    ptoRequests = it,
                    selectedAdmins = it.let {
                        selectedAdmins.map { mmUser ->
                            mmUser?.let { admins ->
                                Admins(
                                    displayName = admins.displayName!!,
                                    userType = admins.userType!!,
                                    photoUrl = admins.photoUrl!!,
                                    designation = admins.designation!!
                                )
                            }
                        }
                    }
                )
            }
        }
    }

    fun updatePtoList(
        selectedAdmins: List<MMUser?>,
        ptoList: List<LocalDate>,
        email: String?,
        desc: String?
    ) {
        val list = mutableListOf<SetGetPtoRequests>()
        ptoList.forEach {
            val obj =
                SetGetPtoRequests(
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
}