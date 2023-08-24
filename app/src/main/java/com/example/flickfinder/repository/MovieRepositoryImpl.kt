package com.example.flickfinder.repository

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
        remoteDataSource.getUpcomingMovies().results.forEach { movie ->
            dataSourceLocal.saveMovie(movie.toMovieEntity("upcoming"))
        }

        return dataSourceLocal.getUpcomingMovies()
    }

    override suspend fun getTopRatedMovies(): MovieList {
        remoteDataSource.getTopRatedMovies().results.forEach { movie ->
            dataSourceLocal.saveMovie(movie.toMovieEntity("toprated"))
        }
        return dataSourceLocal.getTopRatedMovies()
    }

    override suspend fun getPopularMovies(): MovieList {
        remoteDataSource.getPopularMovies().results.forEach { movie ->
            dataSourceLocal.saveMovie(movie.toMovieEntity("popular"))
        }
        return dataSourceLocal.getPopularMovies()
    }

}