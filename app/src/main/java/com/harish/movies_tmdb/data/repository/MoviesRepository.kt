package com.harish.movies_tmdb.data.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData

import com.harish.movies_tmdb.data.db.MoviesDatabase
import com.harish.movies_tmdb.data.network.MoviesAPI
import com.harish.movies_tmdb.models.MovieItem
import com.harish.movies_tmdb.models.MoviesResponse
import com.harish.movies_tmdb.models.MoviesUIGroup
import com.harish.movies_tmdb.utils.Utils
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
    var networkEvents =MutableLiveData<String>()

    fun getMoviesFromServer(scope: CoroutineScope) {
        api.getMoviesFromServer().enqueue(object : Callback<MoviesResponse> {
            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                 if (Utils.hasInternet(context))
                     networkEvents.postValue("Some thing went wrong")
                else
                     networkEvents.postValue("No internet")
            }

            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {
                if (response.isSuccessful) {
                    insertMovies(response.body()!!.results, scope)
                } else {

                    networkEvents.postValue("Server error")
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

