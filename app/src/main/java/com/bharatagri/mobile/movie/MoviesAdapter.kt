package com.bharatagri.mobile.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bharatagri.mobile.R
import com.bharatagri.mobile.service.modal.Movie
import com.bharatagri.mobile.utils.Util.getDateFromString
import com.bharatagri.mobile.utils.Util.getDateString
import com.bharatagri.mobile.utils.hide
import com.bharatagri.mobile.utils.setImage
import kotlinx.android.synthetic.main.item_movie_list.view.*

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    private val movieList = ArrayList<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie_list, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun getItemCount() = movieList.size

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie) {
            with(itemView) {
                movie.apply {
                    ivPoster.setImage(posterPath)
                    tvTitle.text = title
                    getDateFromString(releaseDate)?.let {
                        tvReleaseDate.text = getDateString(it)
                    } ?: tvReleaseDate.hide()
                }
            }
        }
    }

    fun updateData(movies: ArrayList<Movie>) {
        movieList.addAll(movies)
        notifyDataSetChanged()
    }
}