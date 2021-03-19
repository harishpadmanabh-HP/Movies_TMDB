package com.harish.movies_tmdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.harish.movies_tmdb.adapters.AllMoviesAdapter
import com.harish.movies_tmdb.models.MoviesUIGroup
import com.harish.movies_tmdb.viewmodels.MovieViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewModel()
        viewModel.getMoviesFromServer()
        setupObservers()
    }


    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
    }

    private fun setupObservers() {
        viewModel.moviesData.observe(this, Observer {
            viewModel.handleMoviesGrouping(it).observe(this, Observer {
                setupNestedRecyclerView(it)
            })
        })

        viewModel.events.observe(this, Observer {
            Toast.makeText(this, "$it", Toast.LENGTH_SHORT).show()
        })
    }

    private fun setupNestedRecyclerView(data: List<MoviesUIGroup>) {
        val adapter = AllMoviesAdapter()
        adapter.submitList(data)
        main_movie_nested_recyclerview.adapter = adapter
        load_layout.visibility = View.GONE
        main_movie_nested_recyclerview.visibility=View.VISIBLE
    }
}