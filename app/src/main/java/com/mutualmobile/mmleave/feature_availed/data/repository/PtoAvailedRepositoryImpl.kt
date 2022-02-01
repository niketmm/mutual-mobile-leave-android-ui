package com.mutualmobile.mmleave.feature_availed.data.repository

import com.mutualmobile.mmleave.local_cached_data.StoreUserInfo
import com.mutualmobile.mmleave.feature_pto.domain.model.FirebasePtoRequestModel
import com.mutualmobile.mmleave.feature_availed.data.availed.AvailedPtoService
import com.mutualmobile.mmleave.feature_availed.domain.repository.PtoAvailedRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PtoAvailedRepositoryImpl @Inject constructor(
    private val availedPtoService: AvailedPtoService,
    private val storeUserInfo: StoreUserInfo
) : PtoAvailedRepository {

    override suspend fun fetchAllPtoRequests(): Flow<List<FirebasePtoRequestModel?>> {
        return availedPtoService.fetchAllPtoRequests()
    }

    override fun getTotalPtoLeft(): Flow<Int> {
        return storeUserInfo.getUserTotalPto
    }
}