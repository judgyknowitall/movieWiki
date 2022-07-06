package com.example.moviewiki.realm

import android.app.Application
import android.util.Log
import com.example.moviewiki.model.SampleMovie

import io.realm.Realm


class RealmModule(application: Application) {

    private var syncedRealm: Realm? = null

    init {
        Log.d("Realm Module", "Setting up realm...")
        Realm.init(application) // Required for local or remote.
        initializeCollectionIfEmpty()
    }

    /**
     * This is mainly helpful to convey the schema and rules of the EmojiTile, to the Realm Sync Server by writing an instance of the object.
     */
    private fun initializeCollectionIfEmpty() {
        syncedRealm?.executeTransactionAsync { realm ->
            if (realm.where(RealmMovie::class.java).count() == 0L) {
                realm.insert(RealmMovie.convertMovie(SampleMovie.movie))
            }
        }
    }

    /**
     * Checks if the realm is initialized.
     * Generally, syncedRealm should only be exposed as a non-null type.
     */
    fun isInitialized() = syncedRealm != null

    fun getSyncedRealm() : Realm = syncedRealm ?: throw IllegalStateException("Realm is null!")
}