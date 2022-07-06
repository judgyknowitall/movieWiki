package com.example.moviewiki.model

import androidx.compose.runtime.Immutable

/**
 * Ui Events used for communication between the user and the reducer (for state-updates)
 */
@Immutable
sealed class MainScreenUiEvent {
    data class OnSearchBarTextChanged(val newText: String) : MainScreenUiEvent()
    data class ShowMovieList(val movies: List<Movie>) : MainScreenUiEvent()
    data class InternetConnected(val connected: Boolean) : MainScreenUiEvent()
}
