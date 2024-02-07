package com.example.movieapp.api

import com.example.movieapp.model.MovieDetail
import com.example.movieapp.model.SearchResults
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface ApiService {
    @GET("?type=movie")
    suspend fun getSearchResultData(
        @Query(value = "s") searchTitle: String, @Query(value = "apiKey") apiKey: String, @Query(
            value = "page"
        ) pageIndex: Int
    ): Response<SearchResults>
    @GET("?plot=full")
    suspend fun getMovieDetailData(@Query(value = "t") title: String, @Query(value = "apiKey") apiKey: String): Response<MovieDetail>

}