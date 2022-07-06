package com.example.moviewiki.model

import androidx.compose.runtime.Immutable

/**
 * Main State of the App
 * Keeps track of user inputs and search results
 * The Reducer replaces the old state with a new one upon a MainScreenUiEvent
 * The MainScreen uses the states to know what to display
 */
@Immutable
data class MainScreenState(
    val isLoading: Boolean,
    val movies: List<Movie>,
    val connected: Boolean,
    val searchInput: String
) {
    companion object {
        fun init() = MainScreenState(
            isLoading = false,
            movies = emptyList(),
            connected = false,
            searchInput = ""
        )
    }
}