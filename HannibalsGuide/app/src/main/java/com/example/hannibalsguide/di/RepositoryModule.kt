package com.example.hannibalsguide.di

import com.example.hannibalsguide.data.repository.LandmarkRepositoryImpl
import com.example.hannibalsguide.domain.repository.LandmarkRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindLandmarkRepository(
        impl: LandmarkRepositoryImpl
    ): LandmarkRepository
}