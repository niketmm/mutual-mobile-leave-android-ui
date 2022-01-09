package com.mutualmobile.mmleave.services.database.ptorequest

import com.mutualmobile.mmleave.data.model.MMUser
import com.mutualmobile.mmleave.firestore.PtoProperties
import kotlinx.coroutines.flow.Flow

interface PtoRequestService {
  suspend fun makePtoRequest(ptoProperties: PtoProperties)
  suspend fun approvePtoRequest(ptoProperties: PtoProperties)
}