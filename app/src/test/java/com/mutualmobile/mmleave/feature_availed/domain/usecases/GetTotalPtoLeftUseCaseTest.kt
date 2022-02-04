package com.mutualmobile.mmleave.feature_availed.domain.usecases

import com.google.common.truth.Truth.assertThat
import com.mutualmobile.mmleave.feature_availed.data.repository.FakeAvailedRepositoryImpl
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetTotalPtoLeftUseCaseTest {

    private lateinit var getTotalPtoLeftUseCase: GetTotalPtoLeftUseCase
    private lateinit var fakePtoAvailedRepoImpl : FakeAvailedRepositoryImpl

    @Before
    fun setup(){
        fakePtoAvailedRepoImpl = FakeAvailedRepositoryImpl()
        getTotalPtoLeftUseCase = GetTotalPtoLeftUseCase(fakePtoAvailedRepoImpl)
    }

    @Test
    fun `fetch total pto cache data, gets non null value`() {
        runBlocking {
            getTotalPtoLeftUseCase.invoke().collectLatest {
                assertThat(it).isGreaterThan(0)
            }
        }
    }

    @Test
    fun `fetch total pto cache data, below 24 counts`(){
        runBlocking {
            getTotalPtoLeftUseCase.invoke().collect{
                assertThat(it).isLessThan(25)
            }
        }
    }
}