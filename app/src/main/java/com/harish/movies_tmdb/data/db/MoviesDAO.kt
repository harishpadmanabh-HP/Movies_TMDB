package com.harish.movies_tmdb.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.harish.movies_tmdb.models.MovieItem

@Dao
interface MoviesDAO {

    @Query("SELECT * FROM movies_tb ")
    fun getMoviesFromDb():LiveData<List<MovieItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie:MovieItem)

}