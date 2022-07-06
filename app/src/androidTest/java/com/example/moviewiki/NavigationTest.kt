package com.example.moviewiki

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Test Navigation from multiple screens
 * MainScreen -> MovieDescriptionScreen
 * MovieDescriptionScreen -> MainScreen
 */
@RunWith(AndroidJUnit4::class)
class NavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule(MainActivity::class.java)

    @Test
    fun navigationTest() {
        composeTestRule.setContent {
            MovieWikiApp()
        }

        // Search to populate movielist
        composeTestRule
            .onNodeWithTag("SearchBar")
            .performTextInput("spiderman")

        // Wait for results
        Thread.sleep(5000)

        // click on first result
        composeTestRule
            .onAllNodesWithTag("MovieItemButton")
            .onFirst()
            .performClick()

        // Make sure item is the same-ish
        composeTestRule
            .onNodeWithTag("MovieDescription")
            .assertTextContains("spider", substring = true, ignoreCase = true)

        // If close description button exists, click on it
        composeTestRule
            .onNodeWithTag("CloseDescriptionButton")
            .performClick()

        // Make sure movieList is still the same
        composeTestRule
            .onAllNodesWithTag("MovieItemButton")
            .assertAny(hasText("spider", substring = true, ignoreCase = true))
    }
}