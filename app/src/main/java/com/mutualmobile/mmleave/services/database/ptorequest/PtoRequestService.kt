package com.mutualmobile.mmleave.services.database.ptorequest

import com.mutualmobile.mmleave.firestore.SetGetPtoRequests

interface PtoRequestService {
  suspend fun makePtoRequest(ptoProperties: List<SetGetPtoRequests?>)
  suspend fun approvePtoRequest(ptoProperties: SetGetPtoRequests)
}