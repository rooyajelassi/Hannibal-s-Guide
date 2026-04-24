package com.example.hannibalsguide.presentation.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hannibalsguide.domain.model.ChatMessage
import com.example.hannibalsguide.domain.model.Landmark
import com.example.hannibalsguide.domain.usecase.GetChatResponseUseCase
import com.example.hannibalsguide.domain.usecase.GetLandmarksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getChatResponseUseCase: GetChatResponseUseCase,
    private val getLandmarksUseCase: GetLandmarksUseCase
) : ViewModel() {

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    private var currentLandmark: Landmark? = null

    fun init(landmarkId: String) {
        if (currentLandmark != null) return

        viewModelScope.launch {
            currentLandmark = getLandmarksUseCase().find { it.id == landmarkId }
            currentLandmark?.let {
                _messages.value = listOf(
                    ChatMessage(
                        text = "Hi! I'm Tarek. Ask me anything about ${it.name}.",
                        isUser = false
                    )
                )
            }
        }
    }

    fun sendMessage(userText: String) {
        val landmark = currentLandmark ?: return
        if (userText.isBlank()) return

        val userMsg = ChatMessage(text = userText, isUser = true)
        _messages.value = _messages.value + userMsg

        val loadingMsg = ChatMessage(text = "Typing...", isUser = false)
        _messages.value = _messages.value + loadingMsg

        viewModelScope.launch {
            try {
                val answer = getChatResponseUseCase(landmark, userText)
                _messages.value = _messages.value.dropLast(1) + ChatMessage(
                    text = answer,
                    isUser = false
                )
            } catch (e: Exception) {
                _messages.value = _messages.value.dropLast(1) + ChatMessage(
                    text = "Network/API error. Please try again.",
                    isUser = false
                )
            }
        }
    }
}