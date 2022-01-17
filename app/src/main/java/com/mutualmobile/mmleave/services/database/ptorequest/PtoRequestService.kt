package com.mutualmobile.mmleave.services.database.ptorequest

import com.mutualmobile.mmleave.data.model.PtoRequestDomain
import com.mutualmobile.mmleave.data.ui_event.PtoRequestEvents
import kotlinx.coroutines.flow.Flow


interface PtoRequestService {
  suspend fun makePtoRequest(ptoRequests: List<PtoRequestDomain?>, selectedAdmins : List<String?>) : Flow<PtoRequestEvents>
  suspend fun approvePtoRequest(ptoRequestDomain: PtoRequestDomain)
}