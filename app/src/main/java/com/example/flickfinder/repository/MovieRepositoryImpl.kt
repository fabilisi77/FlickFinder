package com.example.flickfinder.repository

import com.example.flickfinder.data.model.MovieList
import com.example.flickfinder.data.remote.MovieDataSource

class MovieRepositoryImpl(private val dataSource: MovieDataSource ):MovieRepository {
    override suspend fun getUpcomingMovies(): MovieList {
        return dataSource.getUpcomingMovies()
    }
    //podemos quitar el return y las llaves y poner solo el igual (=) override suspend fun getUpComingMovies(): MovieList = dataSource.getUpComingMovies

    override suspend fun getTopRatedMovies(): MovieList {
         return dataSource.getTopRatedMovies()
    }

    override suspend fun getPopularMovies(): MovieList {
        return dataSource.getPopularMovies()
    }

}