package com.mutualmobile.mmleave.services.database.ptorequest

import com.mutualmobile.mmleave.data.model.MMUser
import com.mutualmobile.mmleave.firestore.PtoRequest
import com.mutualmobile.mmleave.firestore.SetGetPtoRequests
import kotlinx.coroutines.flow.Flow

interface PtoRequestService {
  suspend fun makePtoRequest(ptoRequest: List<SetGetPtoRequests?>): Boolean
  suspend fun approvePtoRequest(ptoRequest: SetGetPtoRequests)
  suspend fun fetchAdminList() : Flow<List<MMUser?>>
  suspend fun fetchUsersByUsername(username : String) : Flow<List<MMUser?>>
}