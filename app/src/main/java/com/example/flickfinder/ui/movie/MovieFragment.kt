package com.example.flickfinder.ui.movie

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.flickfinder.R
import com.example.flickfinder.core.Resource
import com.example.flickfinder.data.remote.MovieDataSource
import com.example.flickfinder.databinding.FragmentMovieBinding
import com.example.flickfinder.presentation.MovieViewModel
import com.example.flickfinder.presentation.MovieViewModelFactory
import com.example.flickfinder.repository.MovieRepositoryImpl
import com.example.flickfinder.repository.RetrofitClient


class MovieFragment : Fragment(R.layout.fragment_movie) {
    private lateinit var binding: FragmentMovieBinding
    private val viewModel by viewModels<MovieViewModel> {
        MovieViewModelFactory(
            MovieRepositoryImpl(
                MovieDataSource(RetrofitClient.webservice)
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieBinding.bind(view)

        viewModel.fetchMainScreenMovies().observe(viewLifecycleOwner, Observer {result ->
        when(result){
            is Resource.Loading ->{
                Log.d("Livedata","Loading...")
            }
            is Resource.Succes ->{
                Log.d("LiveData", "Upcoming: ${result.data.first} ")
                Log.d("Livedata", "TopRated: ${result.data.second}")
                Log.d("Livedata", "Popular: ${result.data.third}")
            }
            is Resource.Failure -> {
                Log.d("Error","${result.exception}")
            }
        }

        })
    }

}