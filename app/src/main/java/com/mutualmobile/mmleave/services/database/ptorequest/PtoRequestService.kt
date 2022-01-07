package com.mutualmobile.mmleave.services.database.ptorequest

import com.mutualmobile.mmleave.firestore.PtoRequest

interface PtoRequestService {
  suspend fun makePtoRequest(
    ptoRequest: PtoRequest
  ): Boolean
  suspend fun approvePtoRequest(ptoRequest: PtoRequest)
}