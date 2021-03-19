package com.harish.movies_tmdb.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.harish.movies_tmdb.BEST_RATED
import com.harish.movies_tmdb.NEW_RELEASE
import com.harish.movies_tmdb.NEW_VIDEOS
import com.harish.movies_tmdb.R
import com.harish.movies_tmdb.models.MovieItem
import com.harish.movies_tmdb.models.MoviesUIGroup
import kotlinx.android.synthetic.main.row_main_movie_nested_recyclerview.view.*

object MoviesDiff:DiffUtil.ItemCallback<MoviesUIGroup>(){
    override fun areItemsTheSame(oldItem: MoviesUIGroup, newItem: MoviesUIGroup): Boolean {

        return oldItem.type == newItem.type
    }

    override fun areContentsTheSame(oldItem: MoviesUIGroup, newItem: MoviesUIGroup): Boolean {

        return true
    }

}

class AllMoviesAdapter:ListAdapter<MoviesUIGroup,RecyclerView.ViewHolder>(MoviesDiff){
    override fun getItemViewType(position: Int): Int {
        return getItem(position).type

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            NEW_RELEASE->{
                val view = LayoutInflater.from(parent.context).inflate(R.layout.row_main_movie_nested_recyclerview, parent, false)
                NewReleaseViewHolder(view)
            }
            NEW_VIDEOS->{
                val view = LayoutInflater.from(parent.context).inflate(R.layout.row_main_movie_nested_recyclerview, parent, false)
                NewVideosViewHolder(view)
            }
            BEST_RATED->{
                val view = LayoutInflater.from(parent.context).inflate(R.layout.row_main_movie_nested_recyclerview, parent, false)
                BestRatedViewHolder(view)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        holder.itemView.apply {
            val item = getItem(position)
            when (holder){
                is NewReleaseViewHolder->{
                    item.newRelease?.let {
                        holder.bind(it,item.title)
                    }
                }

                is NewVideosViewHolder->{
                    item.newVideos?.let {
                        holder.bind(it,item.title)
                    }
                }
                is BestRatedViewHolder->{
                    item.bestRated?.let {
                        holder.bind(it,item.title)
                    }
                }
            }
        }
    }


    class NewReleaseViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bind(
            movies: List<MovieItem>,
            title: String
        ){

            itemView.tv_title.text = title
            itemView.rv_row.layoutManager = LinearLayoutManager(itemView.context,RecyclerView.HORIZONTAL,false)
            itemView.rv_row.adapter = NewReleaseAdapter(movies)
        }

    }
    class NewVideosViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bind(
            movies: List<MovieItem>,
            title: String
        ){
            itemView.tv_title.text = title
            itemView.rv_row.layoutManager = LinearLayoutManager(itemView.context,RecyclerView.HORIZONTAL,false)
            itemView.rv_row.adapter = NewVideosAdapter(movies)
        }

    }
    class BestRatedViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bind(
            movies: List<MovieItem>,
            title: String
        ){
            itemView.tv_title.text = title
            itemView.rv_row.layoutManager = LinearLayoutManager(itemView.context,RecyclerView.HORIZONTAL,false)
            itemView.rv_row.adapter = BestRatedAdapter(movies)
        }

    }
}