package com.example.moviewiki.model


import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.example.moviewiki.R
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

/**
 * Movie Object
 * Parcelized so it can be passed as an argument during navigation
 */
@Parcelize
class Movie (
    val title: String = "Title",
    val imageId: Int = R.drawable.movie_icon,
    val imageURL: String = "",
    val crew: String = "",
    val description: String = "Description",
) : Parcelable


class MovieType : NavType<Movie>(isNullableAllowed = false) {

    override fun get(bundle: Bundle, key: String): Movie? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): Movie {
        return Gson().fromJson(value, Movie::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: Movie) {
        bundle.putParcelable(key,value)
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////
// Sample Data (used in Previews and Tests)
///////////////////////////////////////////////////////////////////////////////////////////////////


object SampleMovie {
    val movie = Movie (
        title= "Spiderman",
        imageURL = "https://m.media-amazon.com/images/M/MV5BZWMyYzFjYTYtNTRjYi00OGExLWE2YzgtOGRmYjAxZTU3NzBiXkEyXkFqcGdeQXVyMzQ0MzA0NTM@._V1_.jpg",
        crew = "Marry, Jane, Joe",
        description = "This is such a great movie. We've got Spiderman1, 2, and 3. And then there's the Amazing Spiderman, etc. "
    )
}


object SampleData {
    val moviesSample = listOf(
        Movie(
            title="Spiderman",
            imageURL = "https://static.wikia.nocookie.net/marvelcinematicuniverse/images/b/b6/Amazing_Spider-Man_-_Profile_Pic.png/revision/latest?cb=20211221112531"
        ),
        Movie(
            title="Antman",
            imageURL = "https://m.media-amazon.com/images/M/MV5BMjM2NTQ5Mzc2M15BMl5BanBnXkFtZTgwNTcxMDI2NTE@._V1_FMjpg_UX1000_.jpg"
        ),
        Movie(
            title="Superman",
            imageURL = "https://m.media-amazon.com/images/M/MV5BMTY5ODI4NzMtM2EzYS00ZGFlLThjMjgtODMyN2QwYjBkYTYwXkEyXkFqcGdeQXVyMTEyNzgwMDUw._V1_FMjpg_UX1000_.jpg"
        )
    )
}