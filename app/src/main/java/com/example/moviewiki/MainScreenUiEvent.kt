package com.example.moviewiki

import androidx.compose.runtime.Immutable

@Immutable
sealed class MainScreenUiEvent {
    data class ShowMovieList(val movies: List<Movie>) : MainScreenUiEvent()
    data class OnMovieItemClicked(val movie: Movie) : MainScreenUiEvent()
    data class ShowNoInternetError(val show: Boolean) : MainScreenUiEvent()
}

@Immutable
data class MainScreenState(
    val isLoading: Boolean,
    val movies: List<Movie>,
    val noInternet: Boolean,
    val isShowMovieDialog: Boolean
) {
    companion object {

    }
}