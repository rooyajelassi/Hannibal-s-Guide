package com.example.hannibalsguide.domain.repository

import com.example.hannibalsguide.domain.model.AppLanguage
import kotlinx.coroutines.flow.Flow

interface LanguageRepository {
    val languageFlow: Flow<AppLanguage>
    suspend fun setLanguage(language: AppLanguage)
    suspend fun getLanguage(): AppLanguage
}

