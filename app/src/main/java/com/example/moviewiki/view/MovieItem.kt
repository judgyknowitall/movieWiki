package com.example.moviewiki.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.moviewiki.model.Movie
import com.example.moviewiki.ui.theme.MovieWikiTheme


@Composable
fun MovieItem(movie: Movie){
    // Row, Column, Box(stack)
    Surface(shape = MaterialTheme.shapes.medium, elevation = 2.dp) {
        Row (modifier = Modifier
            .padding(all = 10.dp)
            .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = movie.imageId),
                contentDescription = "Contact profile picture",
                modifier = Modifier
                    .size(50.dp)//.clip(CircleShape)
                    .border(1.5.dp, MaterialTheme.colors.primary, RectangleShape)
            )

            // Add a horizontal space between the image and the column
            Spacer(modifier = Modifier.width(10.dp))

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
        MovieItem(Movie("Spiderman"))
    }
}