package com.example.flickfinder.ui.movie

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.example.flickfinder.R
import com.example.flickfinder.core.Resource
import com.example.flickfinder.data.local.AppDataBase
import com.example.flickfinder.data.local.LocalMovieDataSource
import com.example.flickfinder.data.model.Movie
import com.example.flickfinder.data.remote.RemoteMovieDataSource
import com.example.flickfinder.databinding.FragmentMovieBinding
import com.example.flickfinder.presentation.MovieViewModel
import com.example.flickfinder.presentation.MovieViewModelFactory
import com.example.flickfinder.repository.MovieRepositoryImpl
import com.example.flickfinder.repository.RetrofitClient
import com.example.flickfinder.ui.movie.adapters.concat.MovieAdapter
import com.example.flickfinder.ui.movie.adapters.concat.PopularConcatAdapter
import com.example.flickfinder.ui.movie.adapters.concat.TopRatedConcatAdapter
import com.example.flickfinder.ui.movie.adapters.concat.UpComingConcatAdapter


class MovieFragment : Fragment(R.layout.fragment_movie), MovieAdapter.OnMovieClickListener {
    private lateinit var binding: FragmentMovieBinding
    private val viewModel by viewModels<MovieViewModel> {
        MovieViewModelFactory(
            MovieRepositoryImpl(
                RemoteMovieDataSource(RetrofitClient.webservice),
                LocalMovieDataSource(AppDataBase.getDataBase(requireContext()).movieDao())
             )
        )
    }
    private lateinit var concatAdapter: ConcatAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieBinding.bind(view)

        concatAdapter = ConcatAdapter()

        viewModel.fetchMainScreenMovies().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    concatAdapter.apply {
                        addAdapter(
                            0,
                            UpComingConcatAdapter(
                                MovieAdapter(
                                    result.data.first.results,
                                    this@MovieFragment
                                )
                            )
                        )
                        addAdapter(
                            1,
                            TopRatedConcatAdapter(
                                MovieAdapter(
                                    result.data.second.results,
                                    this@MovieFragment
                                )
                            )
                        )
                        addAdapter(
                            2,
                            PopularConcatAdapter(
                                MovieAdapter(
                                    result.data.third.results,
                                    this@MovieFragment
                                )
                            )
                        )
                    }
                    binding.rvMovie.adapter = concatAdapter
                }

                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE

                    Log.d("Error", "${result.exception}")
                }
            }

        })
    }

    override fun onMovieClick(movie: Movie) {
        val action = MovieFragmentDirections.actionMovieFragmentToMovieDetailFragment(
            movie.poster_path,
            movie.backdrop_path,
            movie.vote_average.toFloat(),
            movie.vote_count,
            movie.overview,
            movie.title,
            movie.original_language,
            movie.release_date,
        )
        findNavController().navigate(action)

    }

}