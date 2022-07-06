package com.example.moviewiki

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.moviewiki.model.SampleData
import com.example.moviewiki.model.SampleMovie
import com.example.moviewiki.view.MovieDescriptionScreen
import com.example.moviewiki.view.MovieItem
import com.example.moviewiki.view.MovieList
import org.junit.Rule
import org.junit.Test

/**
 * Test Basic MainScreen Interface
 * Test MovieList items
 * Test MovieItem order
 * Test MovieDescription Screen items
 */
class InterfaceTest {

    @get:Rule
    //val composeTestRule = createAndroidComposeRule(MainActivity::class.java)
    val composeTestRule = createComposeRule()

    @Test
    fun movieListTest() {
        composeTestRule.setContent {
            MovieList(
                movies = SampleData.moviesSample,
                onItemClicked = {}
            )
        }
        // Check each image corresponds to current title
        for (movie in SampleData.moviesSample) {
            composeTestRule
                .onNodeWithContentDescription(movie.imageURL)
                .assertTextEquals(movie.title)
        }
    }

    @Test
    fun movieItemTest() {
        composeTestRule.setContent {
            MovieItem(movie = SampleMovie.movie)
        }

        // Check for order of items
        composeTestRule
            .onNodeWithTag("MovieItem")
            .onChildren()
            .onFirst()
            .assertContentDescriptionEquals(SampleMovie.movie.imageURL)

        composeTestRule
            .onNodeWithTag("MovieItem")
            .onChildren()
            .onLast()
            .assertTextEquals(SampleMovie.movie.title)
    }

    @Test
    fun movieDescriptionTest() {
        composeTestRule.setContent {
            MovieDescriptionScreen(movie = SampleMovie.movie) {}
        }

        // Check for all items
        composeTestRule
            .onNodeWithContentDescription(SampleMovie.movie.imageURL)
            .assertExists()

        composeTestRule
            .onAllNodesWithText(SampleMovie.movie.title)
            .onFirst()
            .assertExists()

        composeTestRule
            .onNodeWithText(SampleMovie.movie.crew)
            .assertExists()

        composeTestRule
            .onNodeWithText(SampleMovie.movie.description)
            .assertExists()
    }
}