package com.example.moviewiki.view

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.moviewiki.model.Movie
import com.example.moviewiki.model.SampleMovie
import com.example.moviewiki.ui.theme.MovieWikiTheme

/**
 * A single Movie Item (non-clickable)
 * Only displays the image and title of given movie
 */
@Composable
fun MovieItem(movie: Movie){
    Surface(shape = MaterialTheme.shapes.medium, elevation = 2.dp) {
        Row (modifier = Modifier
            .padding(all = 10.dp).fillMaxWidth()
            .semantics { testTag = "MovieItem" },
            verticalAlignment = Alignment.CenterVertically)
        {
            // Movie Image
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(movie.imageURL)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(id = movie.imageId),
                error = painterResource(id = movie.imageId),
                contentDescription = movie.imageURL,
                modifier = Modifier
                    .size(50.dp)//.clip(CircleShape)
                    .border(1.5.dp, MaterialTheme.colors.primary, RectangleShape)
            )

            // Add a horizontal space between the image and the column
            Spacer(modifier = Modifier.width(10.dp))

            // Movie Title
            Text(
                text = movie.title,
                style = MaterialTheme.typography.subtitle2
            )
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
fun MovieItemPreview() {
    MovieWikiTheme {
        MovieItem(SampleMovie.movie)
    }
}