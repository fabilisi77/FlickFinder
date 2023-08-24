package com.example.flickfinder.repository

import com.example.flickfinder.core.InternetCheck
import com.example.flickfinder.data.local.LocalMovieDataSource
import com.example.flickfinder.data.model.MovieEntity
import com.example.flickfinder.data.model.MovieList
import com.example.flickfinder.data.model.toMovieEntity
import com.example.flickfinder.data.remote.RemoteMovieDataSource

class MovieRepositoryImpl(
    private val remoteDataSource: RemoteMovieDataSource,
    private val dataSourceLocal: LocalMovieDataSource
) : MovieRepository {
    override suspend fun getUpcomingMovies(): MovieList {
        return if (InternetCheck.isNetworkAvailable()) {
            remoteDataSource.getUpcomingMovies().results.forEach { movie ->
                dataSourceLocal.saveMovie(movie.toMovieEntity("upcoming"))
            }
            dataSourceLocal.getUpcomingMovies()
        } else {
            dataSourceLocal.getUpcomingMovies()
        }
    }

    override suspend fun getTopRatedMovies(): MovieList {
        return if (InternetCheck.isNetworkAvailable()) {
            remoteDataSource.getTopRatedMovies().results.forEach { movie ->
                dataSourceLocal.saveMovie(movie.toMovieEntity("toprated"))
            }
            dataSourceLocal.getTopRatedMovies()
        } else {
            dataSourceLocal.getTopRatedMovies()
        }
    }


    override suspend fun getPopularMovies(): MovieList {
        return if (InternetCheck.isNetworkAvailable()) {
            remoteDataSource.getPopularMovies().results.forEach { movie ->
                dataSourceLocal.saveMovie(movie.toMovieEntity("popular"))
            }
            dataSourceLocal.getPopularMovies()
        } else {
            dataSourceLocal.getPopularMovies()
        }
    }


}