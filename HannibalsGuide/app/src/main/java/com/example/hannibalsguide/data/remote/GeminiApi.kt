package com.example.hannibalsguide.data.remote

import android.content.Context
import com.google.ai.client.generativeai.GenerativeModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GeminiApi @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val apiKey: String
        get() = context.getString(
            context.resources.getIdentifier("gemini_api_key", "string", context.packageName)
        )

    private val model: GenerativeModel by lazy {
        GenerativeModel(
            modelName = "gemini-1.5-flash",
            apiKey = apiKey
        )
    }

    suspend fun ask(prompt: String): String {
        val response = model.generateContent(prompt)
        return response.text ?: "Sorry, I couldn't generate a response."
    }
}