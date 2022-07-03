package com.example.moviewiki.model

import androidx.compose.runtime.Immutable

@Immutable
sealed class MainScreenUiEvent {
    data class OnSearchBarTextChanged(val newText: String) : MainScreenUiEvent()
    data class ShowMovieList(val movies: List<Movie>) : MainScreenUiEvent()
    data class OnMovieItemClicked(val movie: Movie) : MainScreenUiEvent()
    data class ShowInternetError(val show: Boolean) : MainScreenUiEvent()
}

@Immutable
data class MainScreenState(
    val isLoading: Boolean,
    val movies: List<Movie>,
    val noInternet: Boolean,
    val searchInput: String,
    val selectedMovie: Movie? = null
) {
    companion object {
        fun init() = MainScreenState(
            isLoading = false,
            movies = emptyList(),
            noInternet = true,
            searchInput = ""
        )
    }
}