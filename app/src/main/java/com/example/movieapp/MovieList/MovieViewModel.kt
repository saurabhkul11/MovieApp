package com.example.movieapp.MovieList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.SearchItem
import com.example.movieapp.utils.Constants
import com.example.movieapp.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor (private var repository: HomeRepository):ViewModel() {

    private var pageIndex = 0
    private var totalMovies = 0
    private var movieList = ArrayList<SearchItem>()

    private val _moviesLiveData = MutableLiveData<State<ArrayList<SearchItem>>>()
    val moviesLiveData: LiveData<State<ArrayList<SearchItem>>>
        get() = _moviesLiveData

    private val _movieNameLiveData = MutableLiveData<String>()
    val movieNameLiveData: LiveData<String>
        get() = _movieNameLiveData

    fun getMovies() {
        if (pageIndex == 1) {
            movieList.clear()
            _moviesLiveData.postValue(State.loading())
        } else {
            if (movieList.isNotEmpty() && movieList.last() == null)
                movieList.removeAt(movieList.size - 1)
        }
        viewModelScope.launch(Dispatchers.IO) {
            if (_movieNameLiveData.value != null && _movieNameLiveData.value!!.isNotEmpty()) {
                try {
                    val movieResponse = repository.getMovies(
                        _movieNameLiveData.value!!,
                        Constants.KEY,
                        pageIndex
                    )
                    withContext(Dispatchers.Main) {
                        if (movieResponse.Response == Constants.SUCCESS) {
                            movieList.addAll(movieResponse.search)
                            totalMovies = movieResponse.totalResults?.toInt() ?: 0
                            _moviesLiveData.postValue(State.success(movieList))

                        } else
                            _moviesLiveData.postValue(State.error("error found"))
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        _moviesLiveData.postValue(State.error(e.message!!))
                    }
                }
            }

        }
    }

    fun searchMovie(query: String) {
        _movieNameLiveData.value = query
        pageIndex = 1
        totalMovies = 0
        getMovies()
    }


}