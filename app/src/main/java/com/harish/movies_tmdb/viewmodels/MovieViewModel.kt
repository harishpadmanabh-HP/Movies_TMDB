package com.harish.movies_tmdb.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.harish.movies_tmdb.BEST_RATED
import com.harish.movies_tmdb.NEW_RELEASE
import com.harish.movies_tmdb.NEW_VIDEOS
import com.harish.movies_tmdb.data.db.MoviesDatabase
import com.harish.movies_tmdb.data.repository.MoviesRepository
import com.harish.movies_tmdb.models.MovieItem
import com.harish.movies_tmdb.models.MoviesUIGroup

class MovieViewModel(val app: Application) : AndroidViewModel(app) {

    val moviesRepository = MoviesRepository(app)
    val moviesDao = MoviesDatabase.getDatabase(app).moviesDao()
    val moviesData = moviesDao.getMoviesFromDb()

    fun getMoviesFromServer() = moviesRepository.getMoviesFromServer(viewModelScope)
    fun getMovies(allMovies: List<MovieItem>): LiveData<List<MoviesUIGroup>> {

        val movies = MediatorLiveData<List<MoviesUIGroup>>()
        //var allMovies = moviesDao.getMoviesFromDb()
        val result = processMovieGroups(allMovies)
        movies.postValue(result)
        return movies

    }

    fun processMovieGroups(movies: List<MovieItem>): List<MoviesUIGroup> {
        val items = ArrayList<MoviesUIGroup>()

        val newRelease = movies
        val newVideos = movies.filter {
            it.backdropPath != null
        }
        val bestRated = movies.filter {
            it.voteAverage.toInt() > 5
        }

        if (!newRelease.isNullOrEmpty()) {
            items.add(MoviesUIGroup("New Release", NEW_RELEASE).also {
                it.newRelease = newRelease
            })
        }

        if (!newVideos.isNullOrEmpty()) {
            items.add(MoviesUIGroup("New Videos", NEW_VIDEOS).also {
                it.newVideos = newVideos
            })
        }

        if (!bestRated.isNullOrEmpty()) {
            items.add(MoviesUIGroup("Best Rated", BEST_RATED).also {
                it.bestRated = bestRated
            })
        }
        return items
    }


}