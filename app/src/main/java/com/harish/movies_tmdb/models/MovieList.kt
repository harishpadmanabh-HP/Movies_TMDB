package com.harish.movies_tmdb.models


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.harish.movies_tmdb.MOVIES_TABLE


    @Entity(tableName = "movies_tb")
    data class MovieItem(

        @ColumnInfo(name="backdrop_path")
        @SerializedName("backdrop_path")
        val backdropPath: String?,


        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name = "id")
        @SerializedName("id")
        val id: Int,

        @ColumnInfo(name = "original_language")
        @SerializedName("original_language")
        val originalLanguage: String,

        @ColumnInfo(name = "original_title")
        @SerializedName("original_title")
        val originalTitle: String,

        @ColumnInfo(name = "overview")
        @SerializedName("overview")
        val overview: String,

        @ColumnInfo(name = "poster_path")
        @SerializedName("poster_path")
        val posterPath: String,

        @ColumnInfo(name ="vote_average" )
        @SerializedName("vote_average")
        val voteAverage: Float

    )
