package com.example.movieapp.MovieDetails

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.movieapp.api.ApiService
import com.example.movieapp.api.RetrofitHelper
import com.example.movieapp.databinding.ActivityMovieDetailBinding
import com.example.movieapp.utils.Constants
import com.example.movieapp.utils.State
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailActivity: AppCompatActivity() {
    lateinit var binding: ActivityMovieDetailBinding
    lateinit var viewModel: MovieDetailViewModel
    private var movieTitle = ""
    private var moviePoster = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()
        setupAPICall()
        setupUI()

    }

    private fun setupAPICall() {

        movieTitle= intent?.getStringExtra("title_key").toString()
        moviePoster=intent?.getStringExtra("poster_key").toString()
        Glide.with(this).load(moviePoster)
            .thumbnail(0.5f)
            .into(binding.imgPoster)


    }


    private fun setupViewModel() {
//        val api= RetrofitHelper.getInstance().create(ApiService::class.java)
//        viewModel=ViewModelProvider(this,MovieDetailViewModelFactory(MovieDetailRepository(api))).get(MovieDetailViewModel::class.java)

        viewModel=ViewModelProvider(this).get(MovieDetailViewModel::class.java)
    }

    private fun setupUI() {
        viewModel.movieDetailLiveData.observe(this, Observer { state ->
            when (state) {
                is State.Loading -> {
                    binding.progressBar.visibility=View.VISIBLE
                    binding.llDetail.visibility=View.GONE

                }
                is State.Success -> {
                    binding.progressBar.visibility=View.GONE
                    binding.llDetail.visibility=View.VISIBLE

                    state.data.let {
                        binding.textYear.text = "Year: ${it.Year}"
                        binding.textDirector.text = "Director: ${it.Director}"
                        binding.textWriter.text = "Writer: ${it.Writer}"
                        binding.textPlot.text = it.Plot
                        if (it.Ratings.isNotEmpty())
                            binding.textImd.text =
                                "Internet Movie Database: ${it.Ratings[0].Value}"
                        binding.textMetascore.text = "Metascore: ${it.Metascore}"
                        binding.textImdbRating.text = "IMBD Rating: ${it.imdbRating}"
                    }
                }
                is State.Error -> {
                    binding.progressBar.visibility=View.GONE
                    binding.llDetail.visibility=View.GONE
                    Toast.makeText(this,"Error", Toast.LENGTH_SHORT).show()
                }
            }
        })
        getMoviesDetail(movieTitle)
    }
    private fun getMoviesDetail(movieTitle: String) {
        viewModel.getMovieDetail(movieTitle)
    }
}