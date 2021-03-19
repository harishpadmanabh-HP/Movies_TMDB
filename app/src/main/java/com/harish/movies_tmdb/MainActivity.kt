package com.harish.movies_tmdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.harish.movies_tmdb.adapters.AllMoviesAdapter
import com.harish.movies_tmdb.viewmodels.MovieViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        viewModel.getMoviesFromServer()
        viewModel.moviesData.observe(this, Observer {
         viewModel.getMovies(it).observe(this, Observer {
             val adapter = AllMoviesAdapter()
             adapter.submitList(it)
             main_movie_nested_recyclerview.adapter=adapter
         })
        })
    }
}