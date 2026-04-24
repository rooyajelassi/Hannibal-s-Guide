package com.example.hannibalsguide.data.repository

import com.example.hannibalsguide.data.LocalDataSource
import com.example.hannibalsguide.domain.model.Landmark
import com.example.hannibalsguide.domain.repository.LandmarkRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LandmarkRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
) : LandmarkRepository {

    override suspend fun getLandmarks(): List<Landmark> {
        return localDataSource.getLandmarks()
    }

    override suspend fun getLandmarkById(id: String): Landmark? {
        return localDataSource.getLandmarks().find { it.id == id }
    }
}