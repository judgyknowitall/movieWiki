package com.example.moviewiki.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.moviewiki.model.Movie
import com.example.moviewiki.R
import com.example.moviewiki.model.SampleMovie
import com.example.moviewiki.ui.theme.MovieWikiTheme

@Composable
fun MovieDescriptionScreen(movie: Movie){
    // Row, Column, Box(stack)
    Column (modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(rememberScrollState())) {

        // Image
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(movie.imageURL)
                .crossfade(true)
                .build(),
            placeholder = painterResource(id = movie.imageId),
            error = painterResource(id = movie.imageId),
            contentDescription = "Movie Preview Image",
            modifier = Modifier
                .fillMaxWidth()
                //.align(Alignment.CenterHorizontally)
            //.border(1.5.dp, MaterialTheme.colors.primary, RectangleShape)

        )

        Spacer(modifier = Modifier.height(10.dp))

        // Title
        Text(
            text = movie.title,
            color = MaterialTheme.colors.primary,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier
                .padding(all = 10.dp)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Crew and Cast
        DescriptionEntry(title = "Cast", description = movie.cast)
        DescriptionEntry(title = "Crew", description = movie.crew)

        Spacer(modifier = Modifier.height(20.dp))

        // Description
        Text(movie.description)
    }
}

@Composable
fun DescriptionEntry(title: String, description: List<String>) {
    Row (modifier = Modifier.padding(vertical = 2.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(
            title.plus(":"),
            style= MaterialTheme.typography.subtitle2
        )

        Spacer(modifier = Modifier.width(20.dp))

        Text(
            description.joinToString(separator = ", ") { it }
        )
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
fun MovieDescriptionScreenPreview() {
    MovieWikiTheme {
        MovieDescriptionScreen(SampleMovie.movie)
    }
}