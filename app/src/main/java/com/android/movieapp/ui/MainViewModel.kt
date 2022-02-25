package com.android.movieapp.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import com.android.movieapp.App
import com.android.movieapp.model.MovieDetail
import com.android.movieapp.repo.MovieRepo
import com.android.movieapp.service.MovieResponse
import com.android.movieapp.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

data class MainState(
    val movieDetails: List<MovieDetail> = listOf(),
    val selectedMovie: MovieResponse? = null,
    val query: String = "",
    val isLoading: Boolean = false
)

class MainViewModel(
    app: Application,
    private val movieRepo: MovieRepo,
    private val handle: SavedStateHandle
): AndroidViewModel(app) {

    private var searchJob: Job? = null
    private val _state = MutableLiveData(MainState())

    val state: LiveData<MainState> = _state

    fun search(query: String) {
        _state.value = _state.value?.copy(query = query, isLoading = true)
        searchJob?.cancel()
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                val moviesPage1 = movieRepo.getMovies(Constants.apiKey, query, 1)
                val moviesPage2 = movieRepo.getMovies(Constants.apiKey, query, 2)
                _state.postValue(
                    _state.value?.copy(
                        movieDetails = moviesPage1.searches.plus(moviesPage2.searches),
                        isLoading = false
                    )
                )
            } catch (e: Exception) {
                Log.e("VM", e.stackTraceToString())
            }
        }
    }

    fun selectMovie(id: String) {
        _state.value = _state.value?.copy(isLoading = true)
        searchJob?.cancel()
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                val movieDetails = movieRepo.getMovieDetails(Constants.apiKey, id)
                _state.postValue(
                    _state.value?.copy(
                        selectedMovie = movieDetails,
                        isLoading = false
                    )
                )
            } catch (e: Exception) {
                Log.e("VM", e.stackTraceToString())
            }
        }
    }

    fun unselectMovie() {
        _state.postValue(
            _state.value?.copy(
                selectedMovie = null,
                isLoading = false
            )
        )
    }

    companion object {
        fun provideFactory(
            app: App,
            owner: SavedStateRegistryOwner,
            movieRepo: MovieRepo,
        ): ViewModelProvider.Factory = object: AbstractSavedStateViewModelFactory(owner, null) {
            override fun <T : ViewModel?> create(
                key: String,
                modelClass: Class<T>,
                handle: SavedStateHandle
            ): T {
                if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return MainViewModel(app, movieRepo, handle) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}