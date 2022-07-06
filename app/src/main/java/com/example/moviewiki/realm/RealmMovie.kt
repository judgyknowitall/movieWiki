package com.example.moviewiki.realm

import com.example.moviewiki.model.Movie
import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required
import org.bson.types.ObjectId


@RealmClass
open class RealmMovie (
    @PrimaryKey
    var _id : ObjectId = ObjectId.get(),

    @Required
    var title: String = "Title",
    var imageURL: String = "",
    var crew: String = "",
    var description: String = "Description",
) : RealmModel
{
    companion object {
        fun convertMovie(movie: Movie) : RealmMovie {
            return RealmMovie(
                title = movie.title,
                imageURL = movie.imageURL,
                crew = movie.crew,
                description = movie.description
            )
        }
        fun convertRealmMovie(movie: RealmMovie) : Movie {
            return Movie(
                title = movie.title,
                imageURL = movie.imageURL,
                crew = movie.crew,
                description = movie.description
            )
        }
    }
}