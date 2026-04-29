package com.example.hannibalsguide.domain.model

enum class AppLanguage(val code: String, val displayName: String, val isRtl: Boolean) {
    ENGLISH("en", "English", false),
    FRENCH("fr", "Francais", false),
    ARABIC("ar", "العربية", true);

    companion object {
        fun fromCode(code: String?): AppLanguage {
            return values().firstOrNull { it.code == code } ?: ENGLISH
        }
    }
}

