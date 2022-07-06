package com.example.moviewiki.model


import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.example.moviewiki.R
import com.google.gson.Gson
import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import kotlinx.parcelize.Parcelize
import org.bson.types.ObjectId

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
        Movie(title="Spiderman"),
        Movie(title="Antman"),
        Movie(title="Superman")
    )
}