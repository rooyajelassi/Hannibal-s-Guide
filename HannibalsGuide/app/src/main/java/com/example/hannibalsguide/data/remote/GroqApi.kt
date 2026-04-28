package com.example.hannibalsguide.data.remote

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GroqApi @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val apiKey: String by lazy {
        val id = context.resources.getIdentifier("groq_api_key", "string", context.packageName)
        if (id == 0) "" else context.getString(id)
    }

    private val client = OkHttpClient()
    private val gson = Gson()
    private val modelName = "llama-3.1-8b-instant"

    suspend fun ask(prompt: String): String = withContext(Dispatchers.IO) {
        if (apiKey.isBlank()) return@withContext "Groq API key is missing."

        val requestBody = GroqChatRequest(
            model = modelName,
            messages = listOf(GroqMessage(role = "user", content = prompt))
        )

        val request = Request.Builder()
            .url("https://api.groq.com/openai/v1/chat/completions")
            .header("Authorization", "Bearer $apiKey")
            .header("Content-Type", "application/json")
            .post(gson.toJson(requestBody).toRequestBody("application/json".toMediaType()))
            .build()

        return@withContext try {
            client.newCall(request).execute().use { response ->
                val body = response.body?.string().orEmpty()
                if (!response.isSuccessful) {
                    Log.e("GroqApi", "Groq request failed: ${response.code} $body")
                    return@use "Groq error: HTTP ${response.code}"
                }

                val parsed = gson.fromJson(body, GroqChatResponse::class.java)
                parsed.choices.firstOrNull()?.message?.content?.trim().orEmpty()
                    .ifBlank { "No response from Groq." }
            }
        } catch (e: Exception) {
            Log.e("GroqApi", "Groq request failed", e)
            "Groq error: ${e.message ?: "Unknown error"}"
        }
    }
}

private data class GroqChatRequest(
    val model: String,
    val messages: List<GroqMessage>,
    val temperature: Double? = null,
    val max_tokens: Int? = null
)

private data class GroqMessage(
    val role: String,
    val content: String
)

private data class GroqChatResponse(
    val choices: List<GroqChoice> = emptyList()
)

private data class GroqChoice(
    val message: GroqMessage? = null
)
