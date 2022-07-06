package com.example.moviewiki.view

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter.State.Empty.painter
import coil.request.ImageRequest
import com.example.moviewiki.model.Movie
import com.example.moviewiki.model.SampleMovie
import com.example.moviewiki.ui.theme.MovieWikiTheme

/**
 * Movie Description Screen:
 * Displays movie information (image, title, crew&cast, description)
 * TopBar button allows user to navigate to previous screen (MainScreen)
 */
@Composable
fun MovieDescriptionScreen(movie: Movie, onCloseClicked: () -> Unit){

    Scaffold (
        topBar = {
            TopAppBar {
                IconButton(
                    onClick = onCloseClicked,
                    modifier = Modifier.semantics { testTag = "CloseDescriptionButton" })
                {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                }
                Text(movie.title, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start=10.dp))
            }
        })
    {
        Column (modifier = Modifier.fillMaxWidth().padding(all = 10.dp)) {

            Row {
                // Image
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(movie.imageURL)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(id = movie.imageId),
                    error = painterResource(id = movie.imageId),
                    contentDescription = movie.imageURL,
                    modifier = Modifier.fillMaxWidth(0.3f),
                    //.align(Alignment.CenterHorizontally)
                    //.border(1.5.dp, MaterialTheme.colors.primary, RectangleShape)

                )

                Spacer(modifier = Modifier.width(10.dp))

                // Title
                Text(
                    text = movie.title,
                    color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(all = 10.dp)
                        .align(Alignment.CenterVertically)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Box(modifier = Modifier.verticalScroll(rememberScrollState())) {
                Column {
                    // Crew and Cast
                    DescriptionEntry(title = "Cast&Crew", description = movie.crew)

                    Spacer(modifier = Modifier.height(20.dp))

                    // Description
                    Text(
                        movie.description,
                        textAlign = TextAlign.Justify,
                        modifier = Modifier.semantics { testTag = "MovieDescription" }
                    )
                }
            }

        }
    }

}

@Composable
fun DescriptionEntry(title: String, description: String) {
    Row (modifier = Modifier.padding(vertical = 2.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(
            title.plus(":"),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.subtitle2
        )

        Spacer(modifier = Modifier.width(20.dp))

        if (description.isNotEmpty()) {
            Text(description)
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
fun MovieDescriptionScreenPreview() {
    MovieWikiTheme {
        MovieDescriptionScreen(SampleMovie.movie) {
            Log.d("MoveDescriptionPreview","Close Description Screen...")
        }
    }
}