package com.example.moviewiki.view

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.moviewiki.model.Movie
import com.example.moviewiki.model.SampleMovie.movie
import com.example.moviewiki.ui.theme.MovieWikiTheme
import com.example.moviewiki.viewmodel.MainViewModel


@Composable
fun MainScreen(viewModel: MainViewModel, onItemClicked: (Movie) -> Unit) {

    Scaffold (
        topBar = {
            TopAppBar {
                Text("Search for a Movie")
            }
        })
    {
        // Observing state
        val state by viewModel.state.collectAsState()
        val view = LocalView.current

        Column (horizontalAlignment = Alignment.CenterHorizontally) {

            SearchBar (
                text = state.searchInput,
                onValueChanged = { newText -> viewModel.changeSearchBarText(newText) },
                onDoneTyping = { view.clearFocus() },
                onClearClick = {
                    viewModel.changeSearchBarText("")
                    view.clearFocus()
                }
            )

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
    }

}


@Composable
fun SearchBar (
    text: String,
    onValueChanged: (String) -> Unit,
    onClearClick: () -> Unit = {},
    onDoneTyping: () -> Unit = {}
) {
    OutlinedTextField (
        modifier = Modifier.fillMaxWidth().padding(all = 10.dp),
        singleLine = true,
        placeholder = { Text(text = "Search Movies by Name") },
        value = text,
        onValueChange = { newText -> onValueChanged(newText) },
        trailingIcon = {
            IconButton(onClick = onClearClick) {
                Icon(imageVector = Icons.Filled.Clear, contentDescription = "Clear")
            }
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = { onDoneTyping() }
        )
    )
}



@Composable
fun MovieList(movies: List<Movie>, onItemClicked: (Movie) -> Unit) {
    LazyColumn {
        items(movies) { movie ->
            Button(
                onClick = { onItemClicked(movie) },
                modifier = Modifier
                    //.border(0.dp, MaterialTheme.colors.primary, RectangleShape)
                //TODO remove outline??
            ){
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
        MovieList(listOf(Movie("Spiderman"))) {
            Log.d("MovieListPreview", "${movie.title} was selected!")
        }
    }
}

@Preview
@Composable
fun TopBarPreview() {
    var value by remember { mutableStateOf("")}
    SearchBar(
        value,
        onValueChanged = { value = it },
        onClearClick = {
            value = ""
        }
    )
}