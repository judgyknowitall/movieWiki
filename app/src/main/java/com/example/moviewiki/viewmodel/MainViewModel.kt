package com.example.moviewiki.viewmodel

import androidx.lifecycle.ViewModel
import com.example.moviewiki.BuildConfig
import com.example.moviewiki.model.MainScreenState
import com.example.moviewiki.model.MainScreenUiEvent
import com.example.moviewiki.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {

    // Variables
    private val reducer = MainReducer(MainScreenState.init())
    val state : StateFlow<MainScreenState>
        get() = reducer.state


    // Functions
    fun changeSearchBarText(newText: String) {
        reducer.sendEvent(MainScreenUiEvent.OnSearchBarTextChanged(newText))
    }

    fun showInternetError(show: Boolean) {
        reducer.sendEvent(MainScreenUiEvent.ShowInternetError(show))
    }

    fun selectMovie(movie: Movie) {
        reducer.sendEvent(MainScreenUiEvent.OnMovieItemClicked(movie))
    }

    fun showMovies(movies: List<Movie>){
        reducer.sendEvent(MainScreenUiEvent.ShowMovieList(movies))
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