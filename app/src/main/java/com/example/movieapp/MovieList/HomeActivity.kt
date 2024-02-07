package com.example.movieapp.MovieList

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.MovieDetails.MovieDetailActivity
import com.example.movieapp.R
import com.example.movieapp.databinding.ActivityHomeBinding
import com.example.movieapp.utils.RecyclerItemClickListener
import com.example.movieapp.utils.State
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    lateinit var viewModel : MovieViewModel
    lateinit var movie_adapter: MovieListAdapter
    private lateinit var searchView: android.widget.SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setupUI()
        initializeObserver()
        setupAPICall()

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search, menu)
        searchView = menu.findItem(R.id.search).actionView as android.widget.SearchView
        searchView.apply {
            queryHint = "Search"
            isSubmitButtonEnabled = true
        }
        searchData(searchView)
        return true
    }

    private fun setupUI() {
        movie_adapter= MovieListAdapter()
        binding.recv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = movie_adapter
            addOnItemTouchListener(
                RecyclerItemClickListener(
                    applicationContext,
                    object : RecyclerItemClickListener.OnItemClickListener {
                        override fun onItemClick(view: View, position: Int) {
                            if (movie_adapter.getData().isNotEmpty()) {
                                val searchItem = movie_adapter.getData()[position]
                                searchItem?.let {
                                    val intent =
                                        Intent(
                                            applicationContext,
                                            MovieDetailActivity::class.java
                                        )
                                    intent.putExtra("poster_key", it.Poster)
                                    intent.putExtra("title_key", it.Title)
                                    startActivity(intent)
                                }

                            }
                        }

                    })
            )
        }
    }



    private fun setupViewModel() {
        viewModel=ViewModelProvider(this).get(MovieViewModel::class.java)
    }

    private fun initializeObserver() {
        viewModel.movieNameLiveData.observe(this, Observer {

        })

    }

    private fun setupAPICall() {
        viewModel.moviesLiveData.observe(this, Observer { state ->
            when (state) {
                is State.Loading -> {
                    binding.recv.visibility=View.GONE
                    binding.linearLayoutSearch.visibility=View.GONE
                    binding.progressBar.visibility=View.VISIBLE

                }
                is State.Success -> {
                    binding.recv.visibility=View.VISIBLE
                    binding.linearLayoutSearch.visibility=View.GONE
                    binding.progressBar.visibility=View.GONE
                    movie_adapter.setData(state.data)
                }
                is State.Error -> {
                    binding.progressBar.visibility=View.GONE
                    Toast.makeText(this,"Movie Not Found",Toast.LENGTH_SHORT).show()
                }
            }
        })

    }


    fun searchData(searchView: android.widget.SearchView) {
        searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchView.clearFocus()
                viewModel.searchMovie(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }
}