package com.harish.movies_tmdb.data.network

import com.harish.movies_tmdb.BASE_URL
import com.harish.movies_tmdb.models.MoviesResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

interface MoviesAPI{

    @GET("upcoming?api_key=9c0523bff54071c4fb4b716a950231b9&language=en-US&page=1&region=IN|US&with_release_type=2|3")
    fun getMoviesFromServer(): Call<MoviesResponse>

    companion object{
        operator fun invoke(): MoviesAPI {
            var logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

            val okHttpClient = OkHttpClient().newBuilder()
                .connectTimeout(300, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS)
                .writeTimeout(300, TimeUnit.SECONDS)
                .addInterceptor(logger)
                .build()


            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .build()
                .create(MoviesAPI::class.java)
        }
    }
}