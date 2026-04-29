package com.example.hannibalsguide.domain.repository

import com.example.hannibalsguide.domain.model.AppLanguage
import com.example.hannibalsguide.domain.model.Landmark

interface LandmarkRepository {
    suspend fun getLandmarks(language: AppLanguage): List<Landmark>
    suspend fun searchLandmarks(query: String, language: AppLanguage): List<Landmark>
    suspend fun getLandmarkById(id: String, language: AppLanguage): Landmark?
}