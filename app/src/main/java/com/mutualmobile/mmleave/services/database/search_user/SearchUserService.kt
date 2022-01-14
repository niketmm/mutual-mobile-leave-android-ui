package com.mutualmobile.mmleave.services.database.search_user

import com.mutualmobile.mmleave.data.model.MMUser
import kotlinx.coroutines.flow.Flow

interface SearchUserService {
    suspend fun fetchAdminList() : Flow<List<MMUser?>>
    suspend fun fetchAllUserList() : Flow<List<MMUser?>>
    suspend fun fetchUsersByUsername(username : String) : Flow<List<MMUser?>>
}