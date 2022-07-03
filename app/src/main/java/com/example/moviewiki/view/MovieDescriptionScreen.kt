package com.example.moviewiki.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.moviewiki.model.Movie
import com.example.moviewiki.R
import com.example.moviewiki.ui.theme.MovieWikiTheme

@Composable
fun MovieDescriptionScreen(movie: Movie){
    // Row, Column, Box(stack)
    Column (modifier = Modifier.padding(all = 10.dp).fillMaxWidth()) {

        // Image and Title
        Column (modifier = Modifier
            .padding(all = 10.dp)
            .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            // Image
            Image(
                painter = painterResource(id = movie.imageId),
                contentDescription = "Contact profile picture",
                modifier = Modifier
                    .fillMaxWidth()
                //.border(1.5.dp, MaterialTheme.colors.primary, RectangleShape)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Title
            Text(
                text = movie.title,
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.subtitle1
            )
        }

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
        MovieDescriptionScreen(
            Movie(
            title= "Spiderman",
            imageId = R.drawable.profile_picture,
            cast = listOf("Adam", "Tom", "Jerry"),
            crew = listOf("Marry", "Jane", "Joe"),
            description = "This is such a great movie. We've got Spiderman1, 2, and 3. And then there's the Amazing Spiderman, etc. "
        )
        )
    }
}