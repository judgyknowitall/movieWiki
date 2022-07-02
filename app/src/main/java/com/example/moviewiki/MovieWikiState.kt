package com.example.moviewiki


sealed class MovieWikiState {
    object LoadingState : MovieWikiState()
    data class SearchState(val data: List<Movie>) : MovieWikiState()
    data class ErrorState(val data: String) : MovieWikiState()
    data class SelectMovieState(val movie: Movie) : MovieWikiState()
}