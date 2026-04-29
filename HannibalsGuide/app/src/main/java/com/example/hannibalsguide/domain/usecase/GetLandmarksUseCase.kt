package com.example.hannibalsguide.domain.usecase

import com.example.hannibalsguide.domain.model.AppLanguage
import com.example.hannibalsguide.domain.model.Landmark
import com.example.hannibalsguide.domain.repository.LandmarkRepository
import com.example.hannibalsguide.domain.repository.LanguageRepository
import javax.inject.Inject

class GetLandmarksUseCase @Inject constructor(
    private val repository: LandmarkRepository,
    private val languageRepository: LanguageRepository
) {
    suspend operator fun invoke(language: AppLanguage? = null): List<Landmark> {
        val selectedLanguage = language ?: languageRepository.getLanguage()
        return repository.getLandmarks(selectedLanguage)
    }
}