package com.harish.movies_tmdb.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.harish.movies_tmdb.POSTER_PATH
import com.harish.movies_tmdb.R
import com.harish.movies_tmdb.models.MovieItem
import kotlinx.android.synthetic.main.row_new_release.view.*

class NewReleaseAdapter(val movies:List<MovieItem>) :RecyclerView.Adapter<NewReleaseAdapter.ViewHolder>(){
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_new_release, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val item = movies[position]
        holder.itemView.apply {
           Glide.with(context).load("$POSTER_PATH${item.posterPath}").into(iv_movie_poster)
            tv_movie_title.text = item.originalTitle
        }

    }
}