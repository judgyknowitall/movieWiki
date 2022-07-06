package com.example.moviewiki

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.example.moviewiki.model.SampleData
import com.example.moviewiki.view.MovieList
import org.junit.Rule
import org.junit.Test

class MainScreenUITest {

    @get:Rule
    //val composeTestRule = createAndroidComposeRule(MainActivity::class.java)
    val composeTestRule = createComposeRule()

    @Test
    fun searchBarTest() {
        composeTestRule.setContent {

        }
    }

    @Test
    fun movieListTest() {
        composeTestRule.setContent {
            MovieList(
                movies = SampleData.moviesSample,
                onItemClicked = {}
            )
        }
        for (movie in SampleData.moviesSample) {
            // Check titles
            composeTestRule
                .onNodeWithText(movie.title)
                .assertExists("${movie.title} is not shown!")

            // Check Images
            composeTestRule
                .onNodeWithContentDescription(movie.imageURL)
                .assertExists("${movie.title} does not have an image!")
        }
    }

}