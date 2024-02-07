package com.example.movieapp.MovieList

import com.example.movieapp.api.ApiService
import com.example.movieapp.model.SearchResults
import javax.inject.Inject

class HomeRepository @Inject constructor( val apiService: ApiService){

    suspend fun getMovies(
        searchTitle: String,
        apiKey: String,
        pageIndex: Int
    ): SearchResults {

        return   apiService.getSearchResultData(searchTitle, apiKey, pageIndex).body()!!
    }

}