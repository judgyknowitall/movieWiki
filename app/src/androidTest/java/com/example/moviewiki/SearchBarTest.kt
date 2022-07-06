package com.example.moviewiki

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Test searching functionality:
 * Search Bar updates correctly
 * Correct results
 * Correct amount of results
 */
@RunWith(AndroidJUnit4::class)
class SearchBarTest {


    private val searchResults = listOf("Spider-Man", "The Batman", "Star Wars: Return of the Jedi")
    private val searchList = listOf("Spiderman", "Batman", "Star Wars")

    @get:Rule
    val composeTestRule = createAndroidComposeRule(MainActivity::class.java)


    @Test
    fun searchResultsAmountTest() {
        composeTestRule.setContent {
            MovieWikiApp()
        }

        composeTestRule
            .onNodeWithTag("SearchBar")
            .performTextInput("s")

        // Wait for results
        Thread.sleep(5000)

        // "assertCount" doesn't work on lazy columns
        composeTestRule
            .onNodeWithTag("MovieList")
            .performScrollToIndex(19)
    }


    @Test
    fun searchBarUpdateTest() {
        composeTestRule.setContent {
            MovieWikiApp()
        }
        val word = "abcd 2534"

        composeTestRule
            .onNodeWithTag("SearchBar")
            .performTextInput(word)

        composeTestRule
            .onNodeWithTag("SearchBar")
            .assertTextEquals(word)
    }


    @Test
    fun searchTest1() {
        composeTestRule.setContent {
            MovieWikiApp()
        }
        searchTest(0) // Spiderman
    }

    @Test
    fun searchTest2() {
        composeTestRule.setContent {
            MovieWikiApp()
        }
        searchTest(1) // Batman
    }

    @Test
    fun searchTest3() {
        composeTestRule.setContent {
            MovieWikiApp()
        }
        searchTest(2) // Star Wars
    }

    // Helper Function
    private fun searchTest(i : Int) {
        // Search for "SpiderMan"
        composeTestRule
            .onNodeWithTag("SearchBar")
            .performTextInput(searchList[i])

        // Wait for results
        Thread.sleep(5000)

        //composeTestRule.onRoot().printToLog("Testing for ${searchList[i]}")
        composeTestRule
            .onNodeWithText(searchResults[i], useUnmergedTree = true)
            .assertExists("${searchResults[i]} Option doesn't exists!")
    }
}