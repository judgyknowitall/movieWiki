package com.example.moviewiki.model


import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.example.moviewiki.R
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie (
    val title: String = "Title",
    val imageId: Int = R.drawable.profile_picture,
    val crew: List<String> = emptyList(),
    val cast: List<String> = emptyList(),
    val description: String = "Description"
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


object SampleMovie {
    val movie = Movie (
        title= "Spiderman",
        imageId = R.drawable.profile_picture,
        cast = listOf("Adam", "Tom", "Jerry"),
        crew = listOf("Marry", "Jane", "Joe"),
        description = "This is such a great movie. We've got Spiderman1, 2, and 3. And then there's the Amazing Spiderman, etc. "
    )
}


object SampleData {
    val moviesSample = listOf(
        Movie("Spiderman"),
        Movie("Antman"),
        Movie("Superman")
    )
}