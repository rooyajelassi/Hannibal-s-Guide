package com.example.hannibalsguide.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hannibalsguide.domain.model.Landmark
import com.example.hannibalsguide.domain.usecase.GetLandmarksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getLandmarksUseCase: GetLandmarksUseCase
) : ViewModel() {

    private val _landmarks = MutableStateFlow<List<Landmark>>(emptyList())
    val landmarks: StateFlow<List<Landmark>> = _landmarks.asStateFlow()

    private var allLandmarks: List<Landmark> = emptyList()

    fun loadLandmarks() {
        viewModelScope.launch {
            allLandmarks = getLandmarksUseCase()
            _landmarks.value = allLandmarks
        }
    }

    fun search(query: String) {
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