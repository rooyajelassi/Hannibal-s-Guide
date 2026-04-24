package com.example.hannibalsguide.domain.usecase

import com.example.hannibalsguide.domain.model.Landmark
import com.example.hannibalsguide.domain.repository.LandmarkRepository
import javax.inject.Inject

class GetLandmarksUseCase @Inject constructor(
    private val repository: LandmarkRepository
) {
    suspend operator fun invoke(): List<Landmark> = repository.getLandmarks()
}