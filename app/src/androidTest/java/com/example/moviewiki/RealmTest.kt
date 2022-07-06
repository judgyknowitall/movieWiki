package com.example.moviewiki

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Test correct Realm save and retrieve of search results
 */
@RunWith(AndroidJUnit4::class)
class RealmTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule(MainActivity::class.java)

    @Test
    fun saveSearchResultsTest() {
        composeTestRule.setContent {
            MovieWikiApp()
        }

        // Search for "SpiderMan"
        composeTestRule
            .onNodeWithTag("SearchBar")
            .performTextInput("H")

        // Wait for results
        Thread.sleep(5000)

        composeTestRule
            .onNodeWithText("H.")
            .assertExists()

        composeTestRule
            .onNodeWithTag("SaveButton")
            .performClick()
    }

    @Test
    // MUST run after above function!!!
    fun retrieveResultsFromRealmTest() {
        composeTestRule.setContent {
            MovieWikiApp()
        }
        composeTestRule
            .onNodeWithText("H.")
            .assertExists()
    }
}