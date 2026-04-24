package com.example.hannibalsguide.di

import com.example.hannibalsguide.data.repository.ChatRepositoryImpl
import com.example.hannibalsguide.data.repository.LandmarkRepositoryImpl
import com.example.hannibalsguide.domain.repository.ChatRepository
import com.example.hannibalsguide.domain.repository.LandmarkRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryBindingModule {

    @Binds
    @Singleton
    abstract fun bindLandmarkRepository(
        impl: LandmarkRepositoryImpl
    ): LandmarkRepository

    @Binds
    @Singleton
    abstract fun bindChatRepository(
        impl: ChatRepositoryImpl
    ): ChatRepository
}

