package com.example.moviewiki.viewmodel

import android.app.Application
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import be.ceau.itunessearch.Search
import be.ceau.itunessearch.enums.Media
import com.example.moviewiki.model.*
import com.example.moviewiki.model.RealmMovie
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmResults
import io.realm.kotlin.deleteFromRealm
import io.realm.kotlin.where
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Main View Model:
 *
 * Handles App State by using a reducer
 * The Reducer replaces the old state with a new one every time it receives an event
 *
 * Realm is also initialized here and retrieves all items at start-up
 * It deletes old items and adds the new ones upon an event call
 *
 * Internet Connection is also Monitored here
 * The state gets updated every time connection is lost or available again
 */
class MainViewModel(application: Application) : AndroidViewModel(application) {

    // Variables
    private val backgroundThreadRealm: Realm
    private val reducer = MainReducer(MainScreenState.init())
    val state : StateFlow<MainScreenState>
        get() = reducer.state


    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constructor & Destructor

    // Initializer
    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val connectivityManager = application.getSystemService(ConnectivityManager::class.java) as ConnectivityManager
            listenToConnectionState(connectivityManager)
        }
        else {
            Log.d("MainVM_init", "Cannot add a listener to internet connectivity because of old version")
        }

        Realm.init(application.applicationContext)
        val config = RealmConfiguration.Builder()
            .name("MovieWikiRealm")
            .allowQueriesOnUiThread(true)
            .allowWritesOnUiThread(true)
            .deleteRealmIfMigrationNeeded()
            //.migration(Migration())
            .build()
        backgroundThreadRealm = Realm.getInstance(config)
        initRealmDB()
    }

    // Destructor
    override fun onCleared() {
        super.onCleared()
        // Save Realm results here?
        backgroundThreadRealm.close()
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Realm Functions

    private fun initRealmDB() {

        // all tasks in the realm
        val realmMovieResults : RealmResults<RealmMovie>? = backgroundThreadRealm.where<RealmMovie>().findAll()

        // Get old movies
        backgroundThreadRealm.executeTransaction {
            Log.e("REALM", "Retrieving saved movies")
            if (realmMovieResults != null) {
                val movies = ArrayList<Movie>(realmMovieResults.size)
                for (result in realmMovieResults) {
                    movies.add(RealmMovie.convertRealmMovie(result))
                    //result.deleteFromRealm() // Delete old movies
                }
                showMovies(movies)
                Log.e("REALM", "${movies.size} Movies!")
            }
            else Log.e("REALM", "No saved movies")
        }
    }

    fun updateRealmDB() {
        // Delete old movies
        backgroundThreadRealm.executeTransactionAsync { transactionRealm ->
            val results : RealmResults<RealmMovie>? = transactionRealm.where<RealmMovie>().findAll()
            if (results != null) {
                for (realmMovie in results) {
                    realmMovie.deleteFromRealm()
                }
            }
        }

        // Add new movies
        val realmMovies = ArrayList<RealmMovie>(state.value.movies.size)
        state.value.movies.forEach { realmMovies.add(RealmMovie.convertMovie(it)) }
        backgroundThreadRealm.executeTransactionAsync { transactionRealm ->
            Log.d("MainVM_updateRealm", "Adding current search results to Realm")
            transactionRealm.insert(realmMovies)
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Internet Functions

    fun changeConnectivityStatus(connected: Boolean){
        reducer.sendEvent(MainScreenUiEvent.InternetConnected(connected))
    }

    private fun listenToConnectionState(connectivityManager: ConnectivityManager) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network : Network) {
                    Log.d("MainVM_getConnectivity", "The default network is now: $network")
                    changeConnectivityStatus(true)
                }

                override fun onLost(network : Network) {
                    Log.e("MainVM_getConnectivity","The application no longer has a default network. The last default network was $network")
                    changeConnectivityStatus(false)
                }
            })
        }
        /* Older versions?
        else {
            val connected = connectivityManager.allNetworks.any { network ->
                connectivityManager.getNetworkCapabilities(network)
                    ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    ?: false
            }
            changeConnectivityStatus(connected)
        }
         */
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Search Functions

    fun changeSearchBarText(newText: String) {
        reducer.sendEvent(MainScreenUiEvent.OnSearchBarTextChanged(newText))
        if (state.value.connected) search(newText)
    }

    private fun showMovies(movies: List<Movie>){
        reducer.sendEvent(MainScreenUiEvent.ShowMovieList(movies))
        //updateRealmDB()
    }

    // Coroutine
    private fun search(text: String) {
        if (text.isEmpty()) return

        viewModelScope.launch(Dispatchers.Default) {
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
                        crew = result.artistName,
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