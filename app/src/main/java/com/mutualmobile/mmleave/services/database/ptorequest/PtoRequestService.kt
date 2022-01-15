package com.mutualmobile.mmleave.services.database.ptorequest

import com.mutualmobile.mmleave.data.model.Admins
import com.mutualmobile.mmleave.data.model.MMUser
import com.mutualmobile.mmleave.data.model.SetGetPtoRequests


interface PtoRequestService {
  suspend fun makePtoRequest(ptoRequests: List<SetGetPtoRequests?>, selectedAdmins : List<Admins?>)
  suspend fun approvePtoRequest(ptoRequests: SetGetPtoRequests)
}