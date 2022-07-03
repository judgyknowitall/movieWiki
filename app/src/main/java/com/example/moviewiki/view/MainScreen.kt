package com.example.moviewiki.ui

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.moviewiki.model.Movie
import com.example.moviewiki.model.SampleMovie.movie
import com.example.moviewiki.ui.theme.MovieWikiTheme
import com.example.moviewiki.viewmodel.MainViewModel


@Composable
fun MainScreen(viewModel: MainViewModel, onItemClicked: (Movie) -> Unit) {


    // Observing state
    val state by viewModel.state.collectAsState()

    when {
        state.isLoading -> {
            //TODO: show loading bar
            Text("Loading....")
        }
        state.movies.isNotEmpty() -> {
            //TODO show movie list
            MovieList(
                movies = state.movies,
                onItemClicked = onItemClicked
            )
        }
        state.movies.isEmpty() -> {
            //TODO show last searched list, or tell user to start searching / no item found
            Text("No Items to show atm")
        }
    }
}

@Composable
fun MovieList(movies: List<Movie>, onItemClicked: (Movie) -> Unit) {
    LazyColumn {
        items(movies) { movie ->
            Button(onClick = {
                Log.d("DEBUG", "Button works!")
                onItemClicked(movie) //TODO
            }) {
                MovieItem(movie = movie)
            }
        }
    }
}

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


////////////////////////////////////////////////////////////////////////////////////////////////////
// Preview
////////////////////////////////////////////////////////////////////////////////////////////////////


@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun MainScreenPreview() {
    MovieWikiTheme {
        MovieList(listOf(Movie("Spiderman"))) {
            Log.d("MovieListPreview", "${movie.title} was selected!")
        }
    }
}