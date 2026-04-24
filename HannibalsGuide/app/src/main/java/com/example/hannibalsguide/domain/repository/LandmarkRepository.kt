package com.example.hannibalsguide.domain.repository

import com.example.hannibalsguide.domain.model.Landmark

interface LandmarkRepository {
    suspend fun getLandmarks(): List<Landmark>
    suspend fun getLandmarkById(id: String): Landmark?
}