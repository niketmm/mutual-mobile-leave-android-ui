package com.mutualmobile.mmleave.feature_availed.domain.usecases

import com.google.common.truth.Truth.assertThat
import com.mutualmobile.mmleave.feature_availed.data.repository.FakeAvailedRepositoryImpl
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class GetAllPtoAvailedUseCaseTest {

    private lateinit var getAllPtoAvailedUseCase: GetAllPtoAvailedUseCase
    private lateinit var fakePtoAvailedRepoImpl : FakeAvailedRepositoryImpl

    @Before
    fun setup(){
        fakePtoAvailedRepoImpl = FakeAvailedRepositoryImpl()
        getAllPtoAvailedUseCase = GetAllPtoAvailedUseCase(fakePtoAvailedRepoImpl)
    }

    @Test
    fun `fetch availed pto, gets a list`(){
        runBlocking {
            getAllPtoAvailedUseCase.invoke().collect { appliedPtoList ->
                assertThat(appliedPtoList).isNotEmpty()
            }
        }
    }

    @Test
    fun `fetch availed pto,get at-least one item`(){
        runBlocking {
            getAllPtoAvailedUseCase.invoke().collect {
                assertThat(it.size).isGreaterThan(1)
            }
        }
    }

}