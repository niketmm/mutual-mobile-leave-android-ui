package com.mutualmobile.mmleave.feature_pto.data.service

import com.mutualmobile.mmleave.feature_pto.domain.model.PtoRequestDomain
import com.mutualmobile.mmleave.feature_pto.presentation.PtoRequestEvents
import kotlinx.coroutines.flow.Flow


interface PtoRequestService {
  suspend fun makePtoRequest(ptoRequests: List<PtoRequestDomain?>, selectedAdmins : List<String?>) : Flow<PtoRequestEvents>
  suspend fun updateUserPtoDetails(leaveLeft : Int)
  suspend fun approvePtoRequest(ptoRequestDomain: PtoRequestDomain)
}