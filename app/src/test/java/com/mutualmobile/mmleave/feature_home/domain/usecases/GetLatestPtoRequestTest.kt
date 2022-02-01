package com.mutualmobile.mmleave.feature_home.domain.usecases

import com.google.common.truth.Truth.assertThat
import com.google.firebase.Timestamp
import com.mutualmobile.mmleave.feature_home.data.repository.FakeHomeRepository
import com.mutualmobile.mmleave.feature_pto.domain.model.FirebasePtoRequestModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.util.*

class GetLatestPtoRequestTest {

    private lateinit var getLatestPtoRequest: GetLatestPtoRequest
    private lateinit var fakeHomeRepository : FakeHomeRepository

    @Before
    fun setup(){
        fakeHomeRepository = FakeHomeRepository()
        getLatestPtoRequest = GetLatestPtoRequest(fakeHomeRepository)
    }

    @Test
    fun `get latest item of the list`(){
        runBlocking {
            getLatestPtoRequest.invoke().collect {
                assertThat(it).isEqualTo(FirebasePtoRequestModel(date = Timestamp(Date(1008))))
            }
        }
    }

    @Test
    fun `get second last item of the list, reject`(){
        runBlocking {
            getLatestPtoRequest.invoke().collect {
                assertThat(it).isNotEqualTo(FirebasePtoRequestModel(date = Timestamp(Date(1006))))
            }
        }
    }
}