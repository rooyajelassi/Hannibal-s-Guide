package com.example.hannibalsguide.presentation.landmarkdetail

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
class DetailViewModel @Inject constructor(
    private val getLandmarksUseCase: GetLandmarksUseCase
) : ViewModel() {

    private val _landmark = MutableStateFlow<Landmark?>(null)
    val landmark: StateFlow<Landmark?> = _landmark.asStateFlow()

    fun loadLandmark(landmarkId: String) {
        viewModelScope.launch {
            val all = getLandmarksUseCase()
            _landmark.value = all.find { it.id == landmarkId }
        }
    }
}