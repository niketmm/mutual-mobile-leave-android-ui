package com.mutualmobile.mmleave.services.database.ptorequest

import com.mutualmobile.mmleave.data.model.Admins
import com.mutualmobile.mmleave.data.model.MMUser
import com.mutualmobile.mmleave.data.model.SetGetPtoRequests
import com.mutualmobile.mmleave.data.ui_event.PtoRequestEvents
import kotlinx.coroutines.flow.Flow


interface PtoRequestService {
  suspend fun makePtoRequest(ptoRequests: List<SetGetPtoRequests?>, selectedAdmins : List<Admins?>) : Flow<PtoRequestEvents>
  suspend fun approvePtoRequest(ptoRequests: SetGetPtoRequests)
}