package com.example.hannibalsguide.di

import com.example.hannibalsguide.data.preferences.LanguageRepositoryImpl
import com.example.hannibalsguide.data.repository.LandmarkRepositoryImpl
import com.example.hannibalsguide.domain.repository.LanguageRepository
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

    @Binds
    @Singleton
    abstract fun bindLanguageRepository(
        impl: LanguageRepositoryImpl
    ): LanguageRepository
}