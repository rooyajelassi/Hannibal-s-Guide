package com.example.hannibalsguide.presentation.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hannibalsguide.domain.model.AppLanguage
import com.example.hannibalsguide.domain.model.ChatMessage
import com.example.hannibalsguide.domain.model.Landmark
import com.example.hannibalsguide.domain.repository.LanguageRepository
import com.example.hannibalsguide.domain.usecase.GetChatResponseUseCase
import com.example.hannibalsguide.domain.usecase.GetLandmarksUseCase
import com.example.hannibalsguide.presentation.localization.UiStrings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getChatResponseUseCase: GetChatResponseUseCase,
    private val getLandmarksUseCase: GetLandmarksUseCase,
    private val languageRepository: LanguageRepository
) : ViewModel() {

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    private var currentLandmark: Landmark? = null
    private var currentLandmarkId: String? = null
    private var currentLanguage: AppLanguage = AppLanguage.ENGLISH

    init {
        viewModelScope.launch {
            languageRepository.languageFlow.collect { language ->
                currentLanguage = language
                currentLandmarkId?.let { refreshLandmark(it) }
            }
        }
    }

    fun init(landmarkId: String) {
        currentLandmarkId = landmarkId
        viewModelScope.launch {
            refreshLandmark(landmarkId)
        }
    }

    private suspend fun refreshLandmark(landmarkId: String) {
        currentLandmark = getLandmarksUseCase(currentLanguage).find { it.id == landmarkId }
        currentLandmark?.let {
            val strings = UiStrings(currentLanguage)
            _messages.value = listOf(
                ChatMessage(
                    text = strings.greeting(it.name),
                    isUser = false
                )
            )
        }
    }

    fun sendMessage(userText: String) {
        val landmark = currentLandmark ?: return
        if (userText.isBlank()) return

        val userMsg = ChatMessage(text = userText, isUser = true)
        _messages.value = _messages.value + userMsg

        val strings = UiStrings(currentLanguage)
        val loadingMsg = ChatMessage(text = strings.typing, isUser = false)
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
                    text = strings.networkError,
                    isUser = false
                )
            }
        }
    }
}