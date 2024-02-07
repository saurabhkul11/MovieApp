package com.example.movieapp.MovieDetails

import com.example.movieapp.api.ApiService
import com.example.movieapp.model.MovieDetail
import javax.inject.Inject

class MovieDetailRepository @Inject constructor( val apiService: ApiService) {

    suspend fun getMovieDetail(
        title: String,
        apiKey: String
    ): MovieDetail {

        return  apiService.getMovieDetailData(title, apiKey).body()!!
    }

}