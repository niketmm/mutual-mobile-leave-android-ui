package com.mutualmobile.mmleave.services.database.ptorequest

import com.mutualmobile.mmleave.data.model.MMUser
import com.mutualmobile.mmleave.firestore.PtoProperties
import kotlinx.coroutines.flow.Flow

interface PtoRequestService {
  suspend fun makePtoRequest(ptoProperties: PtoProperties)
  suspend fun approvePtoRequest(ptoProperties: PtoProperties)
  // Todo: Separate interface and good Naming convention needed
  suspend fun fetchAdminList() : Flow<List<MMUser?>>
  suspend fun fetchUsersByUsername(username : String) : Flow<List<MMUser>>

}