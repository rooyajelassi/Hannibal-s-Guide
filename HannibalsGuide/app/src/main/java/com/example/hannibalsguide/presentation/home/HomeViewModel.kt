package com.example.hannibalsguide.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hannibalsguide.domain.model.AppLanguage
import com.example.hannibalsguide.domain.model.Landmark
import com.example.hannibalsguide.domain.repository.LanguageRepository
import com.example.hannibalsguide.domain.usecase.GetLandmarksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getLandmarksUseCase: GetLandmarksUseCase,
    private val languageRepository: LanguageRepository
) : ViewModel() {

    private val _landmarks = MutableStateFlow<List<Landmark>>(emptyList())
    val landmarks: StateFlow<List<Landmark>> = _landmarks.asStateFlow()

    private var allLandmarks: List<Landmark> = emptyList()
    private var currentLanguage: AppLanguage = AppLanguage.ENGLISH
    private var currentQuery: String = ""

    init {
        viewModelScope.launch {
            languageRepository.languageFlow.collect { language ->
                currentLanguage = language
                loadLandmarks()
                if (currentQuery.isNotBlank()) {
                    search(currentQuery)
                }
            }
        }
    }

    fun loadLandmarks() {
        viewModelScope.launch {
            allLandmarks = getLandmarksUseCase(currentLanguage)
            _landmarks.value = allLandmarks
        }
    }

    fun search(query: String) {
        currentQuery = query
        if (query.isBlank()) {
            _landmarks.value = allLandmarks
            return
        }

        val q = query.trim().lowercase()
        _landmarks.value = allLandmarks.filter {
            it.name.lowercase().contains(q) || it.city.lowercase().contains(q)
        }
    }
}