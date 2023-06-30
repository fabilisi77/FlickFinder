package com.example.flickfinder.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.flickfinder.core.Resource
import com.example.flickfinder.repository.MovieRepository
import kotlinx.coroutines.Dispatchers

class MovieViewModel(private val repo: MovieRepository) : ViewModel() {

   /** fun fetchUpcomingMovies() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Succes(repo.getUpcomingMovies()))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    } */
    //Creamos un método para hacer el llamado a las 3 listas de peliculas
    fun fetchMainScreenMovies() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Succes(Triple(repo.getUpcomingMovies(),repo.getTopRatedMovies(),repo.getPopularMovies())))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }


}
class MovieViewModelFactory(private val repo: MovieRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(MovieRepository::class.java).newInstance(repo)
    }
}



