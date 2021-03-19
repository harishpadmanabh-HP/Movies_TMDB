package com.harish.movies_tmdb.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.harish.movies_tmdb.models.MovieItem

@Database(entities = [MovieItem::class],version = 2,exportSchema = true)
abstract class MoviesDatabase:RoomDatabase() {
    abstract fun moviesDao():MoviesDAO
    companion object {
        @Volatile
        private var INSTANCE: MoviesDatabase? = null

        fun getDatabase(context: Context): MoviesDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(
                        context
                    )
                        .also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                MoviesDatabase::class.java, "movies_db"
            )
              .build()
    }


}