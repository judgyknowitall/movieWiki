package com.example.moviewiki

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.moviewiki.ui.MainScreen
import com.example.moviewiki.ui.MovieList
import com.example.moviewiki.ui.theme.MovieWikiTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieWikiTheme {
                MainScreen("Android", true)
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