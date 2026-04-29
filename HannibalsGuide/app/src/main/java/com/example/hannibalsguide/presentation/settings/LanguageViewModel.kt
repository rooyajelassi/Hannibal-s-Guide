package com.example.hannibalsguide.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hannibalsguide.domain.model.AppLanguage
import com.example.hannibalsguide.domain.repository.LanguageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val languageRepository: LanguageRepository
) : ViewModel() {

    val language: StateFlow<AppLanguage> = languageRepository.languageFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), AppLanguage.ENGLISH)

    fun setLanguage(language: AppLanguage) {
        viewModelScope.launch {
            languageRepository.setLanguage(language)
        }
    }
}

