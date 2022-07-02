package com.example.moviewiki.ui

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import be.ceau.itunessearch.Search
import be.ceau.itunessearch.enums.Media
import com.example.moviewiki.Movie
import com.example.moviewiki.ui.theme.MovieWikiTheme


@Composable
fun MainScreen(name: String, res: Boolean) {
    /*
    val response = Search("cbs radio")
        .setCountry(Country.CANADA)
        .setAttribute(Attribute.AUTHOR_TERM)
        .setMedia(Media.PODCAST)
        .setLimit(15)
        .execute()

     */
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


    Log.d("DEBUG", "Greetings")
    //${response.results[0].artistName}

    Text(text = "Hello $name! Response: ?")
}

@Composable
fun MovieList(movies: List<Movie>) {
    LazyColumn {
        items(movies) { movie ->
            Button(onClick = {
                Log.d("DEBUG", "Button works!")
            }) {
                MovieItem(movie = movie)
            }
        }
    }
}


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
        MovieList(listOf(Movie("Spiderman")))
    }
}