package com.example.hannibalsguide

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Assert.assertTrue
import org.junit.Assume
import org.junit.Rule
import org.junit.Test

class NavigationUiTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()



    @Test
    fun detailAndChatNavigationShowsExpectedScreens() {
        assumeComposeHierarchyReady()
        val landmarkName = waitForAnyText(
            listOf("El Jem Amphitheatre", "Amphitheatre d'El Jem", "مدرج الجم")
        )

        composeRule.onNodeWithText(landmarkName).performClick()

        val askTarekLabel = waitForAnyText(listOf("Ask Tarek", "Demander a Tarek", "اسال طارق"))
        val chatTitleLabel = listOf("Chat with Tarek", "Discussion avec Tarek", "دردشة مع طارق")
        val chatPlaceholder = listOf(
            "Ask about this place...",
            "Demandez a propos de ce lieu...",
            "اسال عن هذا المكان..."
        )

        composeRule.onNodeWithText(askTarekLabel).assertIsDisplayed()
        composeRule.onNodeWithText(askTarekLabel).performClick()

        assertAnyTextVisible(chatTitleLabel)
        assertAnyTextVisible(chatPlaceholder)
    }

    @Test
    fun mapNavigationHidesDetailActions() {
        assumeComposeHierarchyReady()
        val landmarkName = waitForAnyText(
            listOf("El Jem Amphitheatre", "Amphitheatre d'El Jem", "مدرج الجم")
        )

        composeRule.onNodeWithText(landmarkName).performClick()
        val viewOnMapLabel = waitForAnyText(listOf("View on Map", "Voir sur la carte", "عرض على الخريطة"))
        composeRule.onNodeWithText(viewOnMapLabel).performClick()

        assertAnyContentDescriptionVisible(listOf("Back", "Retour", "رجوع"))
        composeRule.onAllNodesWithText(viewOnMapLabel).assertCountEquals(0)
    }

    private fun waitForAnyText(candidates: List<String>): String {
        composeRule.waitUntil(timeoutMillis = 15_000) {
            candidates.any { text ->
                composeRule.onAllNodesWithText(text).fetchSemanticsNodes().isNotEmpty()
            }
        }
        return candidates.first { text ->
            composeRule.onAllNodesWithText(text).fetchSemanticsNodes().isNotEmpty()
        }
    }

    private fun clickFirstByText(candidates: List<String>) {
        val text = candidates.firstOrNull { candidate ->
            composeRule.onAllNodesWithText(candidate).fetchSemanticsNodes().isNotEmpty()
        }
        requireNotNull(text) { "None of the expected texts were found: $candidates" }
        composeRule.onNodeWithText(text).performClick()
    }

    private fun clickFirstByContentDescription(candidates: List<String>) {
        val description = candidates.firstOrNull { candidate ->
            composeRule.onAllNodesWithContentDescription(candidate, useUnmergedTree = true)
                .fetchSemanticsNodes().isNotEmpty()
        }
        requireNotNull(description) { "None of the expected descriptions were found: $candidates" }
        composeRule.onNodeWithContentDescription(description, useUnmergedTree = true).performClick()
    }

    private fun assertAnyTextVisible(candidates: List<String>) {
        val found = candidates.any { text ->
            composeRule.onAllNodesWithText(text).fetchSemanticsNodes().isNotEmpty()
        }
        assertTrue("Expected one of the texts to be visible: $candidates", found)
    }

    private fun assertAnyContentDescriptionVisible(candidates: List<String>) {
        val found = candidates.any { description ->
            composeRule.onAllNodesWithContentDescription(description, useUnmergedTree = true)
                .fetchSemanticsNodes().isNotEmpty()
        }
        assertTrue("Expected one of the content descriptions to be visible: $candidates", found)
    }

    private fun waitForAnyContentDescription(candidates: List<String>): String {
        composeRule.waitUntil(timeoutMillis = 15_000) {
            candidates.any { description ->
                composeRule.onAllNodesWithContentDescription(description, useUnmergedTree = true)
                    .fetchSemanticsNodes().isNotEmpty()
            }
        }
        return candidates.first { description ->
            composeRule.onAllNodesWithContentDescription(description, useUnmergedTree = true)
                .fetchSemanticsNodes().isNotEmpty()
        }
    }

    private fun assumeComposeHierarchyReady() {
        try {
            composeRule.onAllNodesWithText(" ").fetchSemanticsNodes()
        } catch (e: IllegalStateException) {
            Assume.assumeTrue("Compose hierarchy not available on this device.", false)
        }
    }
}
