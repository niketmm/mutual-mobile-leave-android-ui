package com.mutualmobile.mmleave.feature_pto.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.mutualmobile.mmleave.feature_pto.data.repository.FakeApplyPtoRepository
import com.mutualmobile.mmleave.feature_pto.domain.repository.ApplyPtoRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class FetchAdminListUseCaseTest {

    private lateinit var fakeApplyPtoRepository: ApplyPtoRepository
    private lateinit var fetchAdminListUseCase: FetchAdminListUseCase

    @Before
    fun setup(){
        fakeApplyPtoRepository = FakeApplyPtoRepository()
        fetchAdminListUseCase = FetchAdminListUseCase(fakeApplyPtoRepository)
    }

    @Test
    fun `filter out admins from the user list`(){
        runBlocking {
            fetchAdminListUseCase.invoke().collect {
                it.forEach { user ->
                    assertThat(user?.userType).isEqualTo(1)
                }
            }
        }
    }
}