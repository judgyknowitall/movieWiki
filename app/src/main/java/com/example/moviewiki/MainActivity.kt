package com.example.moviewiki

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.ceau.itunessearch.Search
import be.ceau.itunessearch.enums.Attribute
import be.ceau.itunessearch.enums.Media
import com.example.moviewiki.ui.theme.MovieWikiTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieWikiTheme {
                //MovieList(SampleData.moviesSample)
                //render(initialState)
                Greeting("Android", true)
            }
        }
    }

    //1
    //override fun displayMoviesIntent() = button.clicks()

    //2
    @Composable
    fun render(state: MovieWikiState) {
        when(state) {
            is MovieWikiState.SearchState -> renderDataState(state)
            is MovieWikiState.LoadingState -> renderLoadingState()
            is MovieWikiState.ErrorState -> renderErrorState(state)
            is MovieWikiState.SelectMovieState -> selectMovieState(state)
        }
    }

    //4
    @Composable
    private fun renderDataState(dataState: MovieWikiState.SearchState) {
        //Render movie list
    }

    //3
    @Composable
    private fun renderLoadingState() {
        //Render progress bar on screen
    }

    //5
    @Composable
    private fun renderErrorState(errorState: MovieWikiState.ErrorState) {
        //Display error mesage
    }

    @Composable
    private fun selectMovieState(dataState: MovieWikiState.SelectMovieState) {
        //Render movie description
    }
}

//data class Movie(val name: String)

@Composable
fun MovieCard(msg: Movie){
    // Row, Column, Box(stack)
    Surface(shape = MaterialTheme.shapes.medium, elevation = 2.dp) {
        Row (modifier = Modifier
            .padding(all = 10.dp)
            .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.profile_picture),
                contentDescription = "Contact profile picture",
                modifier = Modifier
                    .size(50.dp)//.clip(CircleShape)
                    .border(1.5.dp, MaterialTheme.colors.primary, RectangleShape)
            )

            // Add a horizontal space between the image and the column
            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = msg.title,
                style = MaterialTheme.typography.subtitle2
            )
        }
    }
}

@Composable
fun MovieList(movies: List<Movie>) {
    LazyColumn {
        items(movies) { movie ->
            Button(onClick = {
                Log.d("DEBUG", "Button works!")
            }) {
                MovieCard(msg = movie)
            }
        }
    }
}

@Composable
fun Greeting(name: String, res: Boolean) {
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



@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun DefaultPreview() {
    MovieWikiTheme {
        MovieList(listOf(Movie("Spiderman")))
    }
}