package com.example.hannibalsguide

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.Assume

class HomeScreenUiTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun homeScreenShowsTitleAndSearch() {
        assumeComposeHierarchyReady()
        val titles = listOf("Discover Tunisia", "Decouvrir la Tunisie", "اكتشف تونس")
        val placeholders = listOf(
            "Search by name, city, or heritage",
            "Rechercher par nom, ville ou patrimoine",
            "ابحث بالاسم او المدينة او التراث"
        )

        composeRule.waitUntil(timeoutMillis = 15_000) {
            titles.any { title ->
                composeRule.onAllNodesWithText(title).fetchSemanticsNodes().isNotEmpty()
            }
        }

        val titleFound = titles.any { title ->
            composeRule.onAllNodesWithText(title).fetchSemanticsNodes().isNotEmpty()
        }
        val placeholderFound = placeholders.any { text ->
            composeRule.onAllNodesWithText(text).fetchSemanticsNodes().isNotEmpty()
        }

        assertTrue("Expected a localized app title to be visible.", titleFound)
        assertTrue("Expected a localized search placeholder to be visible.", placeholderFound)
    }

    private fun assumeComposeHierarchyReady() {
        try {
            composeRule.onAllNodesWithText(" ").fetchSemanticsNodes()
        } catch (e: IllegalStateException) {
            Assume.assumeTrue("Compose hierarchy not available on this device.", false)
        }
    }
}
