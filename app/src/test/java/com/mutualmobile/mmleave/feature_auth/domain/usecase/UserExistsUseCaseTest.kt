package com.mutualmobile.mmleave.feature_auth.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.mutualmobile.mmleave.feature_auth.data.repository.FakeAuthRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class UserExistsUseCaseTest{

    private lateinit var userExistsUseCase : UserExistsUseCase
    private lateinit var fakeAuthRepository : FakeAuthRepositoryImpl

    @Before
    fun setup(){
        fakeAuthRepository = FakeAuthRepositoryImpl()
        userExistsUseCase = UserExistsUseCase(fakeAuthRepository)
    }

    @Test
    fun `provide valid email, accept user`(){
        runBlocking {
            val validEmail = "niket.jain@mutualmobile.com"
            assertThat(userExistsUseCase.invoke(validEmail)).isTrue()
        }
    }

    @Test
    fun `provide invalid email,reject user`(){
        runBlocking {
            val inValidEmail = "niket.jain.com"
            assertThat(userExistsUseCase.invoke(inValidEmail)).isFalse()
        }
    }
}