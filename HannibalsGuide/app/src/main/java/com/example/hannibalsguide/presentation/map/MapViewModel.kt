package com.example.hannibalsguide.presentation.map

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
class MapViewModel @Inject constructor(
    private val getLandmarksUseCase: GetLandmarksUseCase,
    private val languageRepository: LanguageRepository
) : ViewModel() {

    private val _landmarks = MutableStateFlow<List<Landmark>>(emptyList())
    val landmarks: StateFlow<List<Landmark>> = _landmarks.asStateFlow()

    private var currentLanguage: AppLanguage = AppLanguage.ENGLISH

    init {
        viewModelScope.launch {
            languageRepository.languageFlow.collect { language ->
                currentLanguage = language
                _landmarks.value = getLandmarksUseCase(currentLanguage)
            }
        }
    }

    fun getLandmarkLocation(landmarkId: String): Landmark? {
        return _landmarks.value.find { it.id == landmarkId }
    }
}