package com.example.moviewiki.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.ceau.itunessearch.Search
import be.ceau.itunessearch.enums.Media
import com.example.moviewiki.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    // Variables
    private val reducer = MainReducer(MainScreenState.init())
    val state : StateFlow<MainScreenState>
        get() = reducer.state




    // Functions
    fun showInternetError(show: Boolean) {
        reducer.sendEvent(MainScreenUiEvent.ShowInternetError(show))
    }

    fun selectMovie(movie: Movie) {
        reducer.sendEvent(MainScreenUiEvent.OnMovieItemClicked(movie))
    }

    fun changeSearchBarText(newText: String) {
        reducer.sendEvent(MainScreenUiEvent.OnSearchBarTextChanged(newText))
        search(newText)
    }

    private fun showMovies(movies: List<Movie>){
        reducer.sendEvent(MainScreenUiEvent.ShowMovieList(movies))
    }

    // Coroutine
    private fun search(text: String) {
        if (text.isEmpty()) return

        viewModelScope.launch(Dispatchers.Default) {

            // TODO show loading?

            val response = Search(text)
                .setMedia(Media.MOVIE)
                .setLimit(20)
                .execute()

            if (response.results != null){
                val movies: MutableList<Movie> = mutableListOf()
                for (result in response.results) {
                    movies.add(Movie(
                        title = result.trackName,
                        description = result.longDescription,
                        cast = listOf(result.artistName),
                        crew = listOf(result.collectionName),
                        imageURL = result.largestArtworkUrl
                    ))
                }

                if (response.resultCount == 0){
                    Log.d("MainViewModel.search", "Results are empty!")
                }
                else {
                    showMovies(movies)
                }
            }
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Reducer
    ///////////////////////////////////////////////////////////////////////////////////////////////

    private class MainReducer(startState: MainScreenState) {

        private val _state: MutableStateFlow<MainScreenState> = MutableStateFlow(startState)
        val state: StateFlow<MainScreenState>
            get() = _state

        fun sendEvent(event: MainScreenUiEvent) {
            reduce(_state.value, event)
        }

        private fun setState(newState: MainScreenState) {
            _state.tryEmit(newState)
        }

        private fun reduce(oldState: MainScreenState, event: MainScreenUiEvent) {
            when (event) {
                is MainScreenUiEvent.OnSearchBarTextChanged -> {
                    setState(oldState.copy(searchInput = event.newText))
                }
                is MainScreenUiEvent.ShowMovieList -> {
                    setState(oldState.copy(movies = event.movies))
                }
                is MainScreenUiEvent.ShowInternetError -> {
                    setState(oldState.copy(noInternet = event.show))
                }
                is MainScreenUiEvent.OnMovieItemClicked -> {
                    setState(oldState.copy(selectedMovie = event.movie))
                }
            }
        }
    }
}