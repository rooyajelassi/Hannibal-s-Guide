package com.example.hannibalsguide.data.local

import android.content.Context
import com.example.hannibalsguide.data.local.dto.LandmarkDto
import com.example.hannibalsguide.domain.model.AppLanguage
import com.example.hannibalsguide.domain.model.Landmark
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LandmarkLocalDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun loadLandmarks(language: AppLanguage): List<Landmark> {
        return try {
            val fileName = when (language) {
                AppLanguage.ENGLISH -> "landmarks_tn.json"
                AppLanguage.FRENCH -> "landmarks_tn_fr.json"
                AppLanguage.ARABIC -> "landmarks_tn_ar.json"
            }
            val json = context.assets.open(fileName)
                .bufferedReader()
                .use { it.readText() }

            val type = object : TypeToken<List<LandmarkDto>>() {}.type
            val dtos: List<LandmarkDto> = Gson().fromJson(json, type)
            dtos.map { it.toDomain() }
        } catch (e: Exception) {
            emptyList()
        }
    }
}