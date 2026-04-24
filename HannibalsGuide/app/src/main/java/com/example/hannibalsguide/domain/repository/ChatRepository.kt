package com.example.hannibalsguide.domain.repository

import com.example.hannibalsguide.domain.model.Landmark

interface ChatRepository {
    suspend fun getChatResponse(landmark: Landmark, userQuestion: String): String
}