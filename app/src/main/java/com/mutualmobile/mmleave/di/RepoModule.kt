package com.mutualmobile.mmleave.di

import com.mutualmobile.mmleave.data.repo.AuthRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.Single
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepoModule {

    @Provides
    @Singleton
    fun provideAuthRepository() : AuthRepo {
        return AuthRepo()
    }
}