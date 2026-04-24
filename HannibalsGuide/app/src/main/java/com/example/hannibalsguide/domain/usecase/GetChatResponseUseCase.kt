package com.example.hannibalsguide.domain.usecase

import com.example.hannibalsguide.domain.model.Landmark
import com.example.hannibalsguide.domain.repository.ChatRepository
import javax.inject.Inject

class GetChatResponseUseCase @Inject constructor(
    private val repository: ChatRepository
) {
    suspend operator fun invoke(landmark: Landmark, userQuestion: String): String {
        return repository.getChatResponse(landmark, userQuestion)
    }
}