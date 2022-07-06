package com.example.moviewiki.model

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required
import org.bson.types.ObjectId

/**
 * Wrapper for the Movie Class
 * Used to save movies to the local Realm Database
 */
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