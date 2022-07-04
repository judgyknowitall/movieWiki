package com.example.moviewiki.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviewiki.BuildConfig
import com.example.moviewiki.model.*
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
        viewModelScope.launch() {
            showMovies(SampleData.moviesSample)

            // TODO search movie
        /*
        Log.d("DEBUG", "$res")
        if (res){
        //LaunchedEffect(Unit) {
        val response = Search("Nemo")
            .setMedia(Media.MOVIE)
            .setLimit(5)
            .execute()

        if (response.results != null){
            Log.d("DEBUG", "Response: ${response.results[0].trackName}")
        }
        //}
        }
        */
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