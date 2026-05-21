package com.example.hannibalsguide

import com.example.hannibalsguide.domain.model.AppLanguage
import com.example.hannibalsguide.presentation.localization.UiStrings
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class LocalizationLocalTest {
    @Test
    fun fromCodeDefaultsToEnglish() {
        assertEquals(AppLanguage.ENGLISH, AppLanguage.fromCode(null))
        assertEquals(AppLanguage.ENGLISH, AppLanguage.fromCode("unknown"))
    }

    @Test
    fun arabicIsRtl() {
        assertTrue(AppLanguage.ARABIC.isRtl)
    }

    @Test
    fun englishStringsMatchExpected() {
        val strings = UiStrings(AppLanguage.ENGLISH)
        assertEquals("Discover Tunisia", strings.appTitle)
        assertEquals("Settings", strings.settingsTitle)
        assertEquals("Search by name, city, or heritage", strings.searchPlaceholder)
    }
}

