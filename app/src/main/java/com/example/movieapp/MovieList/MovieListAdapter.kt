package com.example.movieapp.MovieList

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.model.SearchItem

class MovieListAdapter( ):
    RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {

    private var moviesList = ArrayList<SearchItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
       return MovieViewHolder(view)
    }

    override fun getItemCount() = moviesList.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        if (moviesList[position] != null) {
            holder.bindItems(moviesList[position]!!)
        }


    }
    fun setData(newMoviesList: ArrayList<SearchItem>) {
        if (newMoviesList != null) {
            if (moviesList.isNotEmpty())
                moviesList.removeAt(moviesList.size - 1)
            moviesList.clear()
            moviesList.addAll(newMoviesList)
        } else {
            moviesList.add(newMoviesList)
        }
        notifyDataSetChanged()
    }
    fun getData() = moviesList

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imagePoster: ImageView = itemView.findViewById(R.id.poster)
        private val textTitle: TextView = itemView.findViewById(R.id.title)
        private val textYear: TextView = itemView.findViewById(R.id.year)

        @SuppressLint("SetTextI18n")
        fun bindItems(movie: SearchItem) {
            textTitle.text = movie.Title
            textYear.text = movie.Year
            Glide.with(imagePoster.context).load(movie.Poster)
                .thumbnail(0.5f)
                .into(imagePoster)
        }

    }
}