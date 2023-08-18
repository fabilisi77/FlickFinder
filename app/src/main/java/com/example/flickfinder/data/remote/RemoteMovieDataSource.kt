package com.example.flickfinder.data.remote

import com.example.flickfinder.data.model.MovieList
import com.example.flickfinder.repository.WebService
import com.example.flickfinder.utils.AppConstants

class RemoteMovieDataSource(private val webService: WebService) {

    suspend fun getUpcomingMovies(): MovieList {
        return webService.getUpcomingMovies(AppConstants.API_KEY)
    }

    suspend fun getTopRatedMovies(): MovieList {
        return webService.getTopRatedMovies(AppConstants.API_KEY)
    }

    suspend fun getPopularMovies(): MovieList {
        return webService.getPopularMovies(AppConstants.API_KEY)
    }
}