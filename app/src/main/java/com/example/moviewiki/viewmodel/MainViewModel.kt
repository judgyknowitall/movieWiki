package com.example.moviewiki.viewmodel

import androidx.lifecycle.ViewModel
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
    fun showNoInternetError() {
        reducer.sendEvent(MainScreenUiEvent.ShowNoInternetError)
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

        private fun reduce(oldState: MainScreenState, event: MainScreenUiEvent) {
            when (event) {
                is MainScreenUiEvent.ShowMovieList -> {
                    // TODO
                }
                is MainScreenUiEvent.ShowNoInternetError -> {
                    // TODO
                }
                is MainScreenUiEvent.OnMovieItemClicked -> {
                    //TODO
                }
            }
        }
    }
}