package com.harish.movies_tmdb.data.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.harish.movies_tmdb.BEST_RATED
import com.harish.movies_tmdb.NEW_RELEASE
import com.harish.movies_tmdb.NEW_VIDEOS
import com.harish.movies_tmdb.data.db.MoviesDatabase
import com.harish.movies_tmdb.data.network.MoviesAPI
import com.harish.movies_tmdb.models.MovieItem
import com.harish.movies_tmdb.models.MoviesResponse
import com.harish.movies_tmdb.models.MoviesUIGroup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesRepository(val context: Context) {


    private val api = MoviesAPI()
    private val moviesDao = MoviesDatabase.getDatabase(context).moviesDao()

    fun getMoviesFromServer(scope: CoroutineScope) {
        api.getMoviesFromServer().enqueue(object : Callback<MoviesResponse> {
            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                Log.e("uimovies", "api call error $t")
            }

            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {
                if (response.isSuccessful) {
                    insertMovies(response.body()!!.results, scope)
                } else {
                    Log.e("uimovies", "response not success")
                }

            }
        })
    }

    fun insertMovies(movies: List<MovieItem>, scope: CoroutineScope) {
        scope.launch(Dispatchers.IO) {
            movies.forEach {
                moviesDao.insertMovie(it)
            }

        }

    }



}

