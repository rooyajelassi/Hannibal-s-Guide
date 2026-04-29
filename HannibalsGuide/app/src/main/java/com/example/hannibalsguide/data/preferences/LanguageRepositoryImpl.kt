package com.example.hannibalsguide.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.hannibalsguide.domain.model.AppLanguage
import com.example.hannibalsguide.domain.repository.LanguageRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "app_settings")

@Singleton
class LanguageRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : LanguageRepository {

    private val languageKey = stringPreferencesKey("app_language")

    override val languageFlow: Flow<AppLanguage> = context.dataStore.data.map { prefs: Preferences ->
        AppLanguage.fromCode(prefs[languageKey])
    }

    override suspend fun setLanguage(language: AppLanguage) {
        context.dataStore.edit { prefs ->
            prefs[languageKey] = language.code
        }
    }

    override suspend fun getLanguage(): AppLanguage {
        return AppLanguage.fromCode(context.dataStore.data.first()[languageKey])
    }
}

