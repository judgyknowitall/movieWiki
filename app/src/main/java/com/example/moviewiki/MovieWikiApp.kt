package com.example.moviewiki

import android.net.Uri
import android.util.Log
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.moviewiki.model.Movie
import com.example.moviewiki.model.MovieType
import com.example.moviewiki.model.NavRoute
import com.example.moviewiki.model.SampleMovie
import com.example.moviewiki.view.MainScreen
import com.example.moviewiki.view.MovieDescriptionScreen
import com.example.moviewiki.ui.theme.MovieWikiTheme
import com.example.moviewiki.viewmodel.MainViewModel
import com.google.gson.Gson

@Composable
fun MovieWikiApp() {
    val navController = rememberNavController()
    MovieWikiTheme {
        Scaffold {
            NavHost(navController = navController, startDestination = NavRoute.MainScreen) {

                // Main Screen (Search and List View)
                composable(NavRoute.MainScreen) {
                    val viewModel = viewModel<MainViewModel>()
                    MainScreen(
                        viewModel = viewModel,
                        onItemClicked = { movie -> navigateToMovie(navController, movie) }
                    )
                }

                // Movie Description Screen
                composable(
                    route = "${NavRoute.MovieDescriptionScreen}/{movie}",
                    arguments = listOf(
                        navArgument("movie") {
                            type = MovieType()
                    })
                ){ backStackEntry ->
                    val movie = backStackEntry.arguments?.getParcelable<Movie>("movie") // extract argument
                    if (movie != null) {
                        MovieDescriptionScreen(
                            movie = movie,
                            onCloseClicked = { navController.navigate(NavRoute.MainScreen) }
                        )
                    }
                    else {
                        Log.e("MovieWikiApp", "Movie not received properly from previous nav")
                        MovieDescriptionScreen(
                            movie = SampleMovie.movie,
                            onCloseClicked = { navController.navigate(NavRoute.MainScreen) }
                        )
                    }
                }
            }
        }
    }
}

private fun navigateToMovie(navController: NavController, movie: Movie) {
    val json = Uri.encode(Gson().toJson(movie))
    navController.navigate("${NavRoute.MovieDescriptionScreen}/$json")
}