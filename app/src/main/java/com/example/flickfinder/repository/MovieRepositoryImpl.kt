package com.example.flickfinder.repository

import com.example.flickfinder.data.model.MovieList
import com.example.flickfinder.data.remote.RemoteMovieDataSource

class MovieRepositoryImpl(private val remoteDataSource: RemoteMovieDataSource) : MovieRepository {
    override suspend fun getUpcomingMovies(): MovieList {
        return remoteDataSource.getUpcomingMovies()
    }
    //podemos quitar el return y las llaves y poner solo el igual (=) override suspend fun getUpComingMovies(): MovieList = dataSource.getUpComingMovies

    override suspend fun getTopRatedMovies(): MovieList {
        return remoteDataSource.getTopRatedMovies()
    }

    override suspend fun getPopularMovies(): MovieList {
        return remoteDataSource.getPopularMovies()
    }

}