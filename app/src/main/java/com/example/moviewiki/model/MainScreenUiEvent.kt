package com.example.moviewiki.model

import androidx.compose.runtime.Immutable

@Immutable
sealed class MainScreenUiEvent {
    data class OnSearchBarTextChanged(val newText: String) : MainScreenUiEvent()
    data class ShowMovieList(val movies: List<Movie>) : MainScreenUiEvent()
    data class InternetConnected(val connected: Boolean) : MainScreenUiEvent()
}

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
            connected = true,
            searchInput = ""
        )
    }
}