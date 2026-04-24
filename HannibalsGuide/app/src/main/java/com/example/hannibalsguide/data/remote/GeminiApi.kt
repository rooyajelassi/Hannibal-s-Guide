package com.example.hannibalsguide.data.remote

import android.content.Context
import android.util.Log
import com.google.ai.client.generativeai.GenerativeModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GeminiApi @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val apiKey: String by lazy {
        val id = context.resources.getIdentifier("gemini_api_key", "string", context.packageName)
        if (id == 0) "" else context.getString(id)
    }

    private val model: GenerativeModel by lazy {
        GenerativeModel(
            modelName = "gemini-2.0-flash",
            apiKey = apiKey
        )
    }

    suspend fun ask(prompt: String): String {
        return try {
            if (apiKey.isBlank()) return "Gemini API key is missing."
            val response = model.generateContent(prompt)
            response.text ?: "No response from Gemini."
        } catch (e: Exception) {
            Log.e("GeminiApi", "Gemini request failed", e)
            "Gemini error: ${e.message ?: "Unknown error"}"
        }
    }
}