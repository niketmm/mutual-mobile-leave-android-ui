package com.mutualmobile.mmleave.feature_pto.data.service

import com.mutualmobile.mmleave.feature_pto.domain.model.MMUser
import kotlinx.coroutines.flow.Flow

interface SearchUserService {
    suspend fun fetchAdminList() : Flow<List<MMUser?>>
    suspend fun fetchAllUserList() : Flow<List<MMUser?>>
    suspend fun fetchUsersByUsername(username : String) : Flow<List<MMUser?>>
}