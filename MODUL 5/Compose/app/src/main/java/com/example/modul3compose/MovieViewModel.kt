package com.example.modul3compose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.modul3compose.data.model.Movie
import com.example.modul3compose.data.repository.MovieRepository
import com.example.modul3compose.util.ApiResult
import com.example.modul3compose.util.PreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class MovieViewModel(
    private val repository: MovieRepository,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    private val _moviesState = MutableStateFlow<ApiResult<List<Movie>>>(ApiResult.Loading)
    val moviesState: StateFlow<ApiResult<List<Movie>>> = _moviesState.asStateFlow()

    private val _selectedMovie = MutableStateFlow<Movie?>(null)
    val selectedMovie: StateFlow<Movie?> = _selectedMovie.asStateFlow()

    private val _clickEvent = MutableStateFlow<ClickEvent?>(null)
    val clickEvent: StateFlow<ClickEvent?> = _clickEvent.asStateFlow()

    sealed class ClickEvent {
        data class DetailClick(val movie: Movie) : ClickEvent()
        data class WebClick(val movie: Movie) : ClickEvent()
    }

    init {
        loadMovies()
    }

    fun loadMovies() {
        viewModelScope.launch {
            repository.getMovies("popular").collect { result ->
                _moviesState.value = result
            }
        }
    }

    fun onDetailClick(movie: Movie) {
        Timber.d("Tombol Detail ditekan → ${movie.title}")
        _clickEvent.value = ClickEvent.DetailClick(movie)
    }

    fun onWebClick(movie: Movie) {
        Timber.d("Tombol Web ditekan → ${movie.title}")
        _clickEvent.value = ClickEvent.WebClick(movie)
    }

    fun resetClickEvent() {
        _clickEvent.value = null
    }

    fun setLanguage(language: String) {
        preferencesManager.selectedLanguage = language
        viewModelScope.launch {
            repository.clearCache("popular")
            repository.clearCache("now_playing")
            loadMovies()
        }
    }

    class Factory(
        private val repository: MovieRepository,
        private val preferencesManager: PreferencesManager
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MovieViewModel(repository, preferencesManager) as T
        }
    }
}