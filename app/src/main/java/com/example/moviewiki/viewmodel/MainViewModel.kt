package com.example.moviewiki.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.ceau.itunessearch.Search
import be.ceau.itunessearch.enums.Media
import com.example.moviewiki.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    // Variables
    private val reducer = MainReducer(MainScreenState.init())
    val state : StateFlow<MainScreenState>
        get() = reducer.state

    // Initializer
    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val connectivityManager = application.getSystemService(ConnectivityManager::class.java) as ConnectivityManager
            listenToConnectionState(connectivityManager)
        }
        else {
            Log.d("MainVM_init", "Cannot add a listener to internet connectivity because of old version")
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Internet Functions

    private fun changeConnectivityStatus(connected: Boolean){
        reducer.sendEvent(MainScreenUiEvent.InternetConnected(connected))
    }

    private fun listenToConnectionState(connectivityManager: ConnectivityManager) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network : Network) {
                    Log.e("MainVM_getConnectivity", "The default network is now: $network")
                    changeConnectivityStatus(true)
                }

                override fun onLost(network : Network) {
                    Log.e("MainVM_getConnectivity","The application no longer has a default network. The last default network was $network")
                    changeConnectivityStatus(false)
                }
            })
        }
        else {
            val connected = connectivityManager.allNetworks.any { network ->
                connectivityManager.getNetworkCapabilities(network)
                    ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    ?: false
            }
            changeConnectivityStatus(connected)
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Search Functions

    fun changeSearchBarText(newText: String) {
        reducer.sendEvent(MainScreenUiEvent.OnSearchBarTextChanged(newText))
        if (state.value.connected) search(newText)
    }

    private fun showMovies(movies: List<Movie>){
        reducer.sendEvent(MainScreenUiEvent.ShowMovieList(movies))
    }

    // Coroutine
    private fun search(text: String) {
        if (text.isEmpty()) return

        viewModelScope.launch(Dispatchers.Default) {

            //TODO show loading?

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
                        crew = listOf(result.artistName),
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
                is MainScreenUiEvent.InternetConnected -> {
                    setState(oldState.copy(connected = event.connected))
                }
            }
        }
    }
}