package com.example.moviewiki

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule
import java.lang.Thread.sleep

/**
 * Instrumented test, which will execute on an Android device.
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class MovieWikiAppTest {

    private val searchResults = listOf("Spider-Man", "The Batman", "Star Wars: Return of the Jedi")
    private val searchList = listOf("Spiderman", "Batman", "Star Wars")

    @get:Rule
    val composeTestRule = createAndroidComposeRule(MainActivity::class.java)

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.moviewiki", appContext.packageName)
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
        sleep(5000)

        composeTestRule.onRoot().printToLog("Testing for ${searchList[i]}")
        composeTestRule
            .onNodeWithText(searchResults[i], useUnmergedTree = true)
            .assertExists("${searchResults[i]} Option doesn't exists!")
    }
}