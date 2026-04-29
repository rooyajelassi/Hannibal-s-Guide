package com.example.hannibalsguide.data.repository

import com.example.hannibalsguide.data.local.LandmarkLocalDataSource
import com.example.hannibalsguide.domain.model.AppLanguage
import com.example.hannibalsguide.domain.model.Landmark
import com.example.hannibalsguide.domain.repository.LandmarkRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LandmarkRepositoryImpl @Inject constructor(
    private val localDataSource: LandmarkLocalDataSource
) : LandmarkRepository {

    private val cachedByLanguage = mutableMapOf<AppLanguage, List<Landmark>>()

    override suspend fun getLandmarks(language: AppLanguage): List<Landmark> {
        return cachedByLanguage.getOrPut(language) { localDataSource.loadLandmarks(language) }
    }

    override suspend fun searchLandmarks(query: String, language: AppLanguage): List<Landmark> {
        val q = query.trim().lowercase()
        if (q.isBlank()) return getLandmarks(language)

        return getLandmarks(language).filter {
            it.name.lowercase().contains(q) ||
                    it.city.lowercase().contains(q) ||
                    it.category.lowercase().contains(q)
        }
    }

    override suspend fun getLandmarkById(id: String, language: AppLanguage): Landmark? {
        return getLandmarks(language).firstOrNull { it.id == id }
    }
}