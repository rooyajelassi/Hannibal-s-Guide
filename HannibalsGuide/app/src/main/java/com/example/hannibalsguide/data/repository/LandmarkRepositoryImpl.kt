package com.example.hannibalsguide.data.repository

import com.example.hannibalsguide.data.local.LandmarkLocalDataSource
import com.example.hannibalsguide.domain.model.Landmark
import com.example.hannibalsguide.domain.repository.LandmarkRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LandmarkRepositoryImpl @Inject constructor(
    private val localDataSource: LandmarkLocalDataSource
) : LandmarkRepository {

    private val cached: List<Landmark> by lazy { localDataSource.loadLandmarks() }

    override suspend fun getLandmarks(): List<Landmark> = cached

    override suspend fun searchLandmarks(query: String): List<Landmark> {
        val q = query.trim().lowercase()
        if (q.isBlank()) return cached

        return cached.filter {
            it.name.lowercase().contains(q) ||
                    it.city.lowercase().contains(q) ||
                    it.category.lowercase().contains(q)
        }
    }

    override suspend fun getLandmarkById(id: String): Landmark? {
        return cached.firstOrNull { it.id == id }
    }
}