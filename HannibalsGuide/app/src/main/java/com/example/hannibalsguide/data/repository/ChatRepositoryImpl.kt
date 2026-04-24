package com.example.hannibalsguide.data.repository

import com.example.hannibalsguide.data.remote.GeminiApi
import com.example.hannibalsguide.domain.model.Landmark
import com.example.hannibalsguide.domain.repository.ChatRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatRepositoryImpl @Inject constructor(
    private val geminiApi: GeminiApi
) : ChatRepository {

    override suspend fun getChatResponse(landmark: Landmark, userQuestion: String): String {
        val prompt = """
            You are Tarek, a helpful Tunisian heritage tour guide.
            Answer clearly and briefly.
            Context landmark:
            - Name: ${landmark.name}
            - City: ${landmark.city}
            - Description: ${landmark.description}
            - History: ${landmark.history}

            User question: $userQuestion
        """.trimIndent()

        return geminiApi.ask(prompt)
    }
}