package com.example.modul3compose.data.repository

import com.example.modul3compose.BuildConfig
import com.example.modul3compose.data.local.MovieDao
import com.example.modul3compose.data.local.MovieEntity
import com.example.modul3compose.data.model.Movie
import com.example.modul3compose.data.remote.TmdbApiService
import com.example.modul3compose.util.ApiResult
import com.example.modul3compose.util.PreferencesManager
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class MovieRepository(
    private val apiService: TmdbApiService,
    private val movieDao: MovieDao,
    private val preferencesManager: PreferencesManager
) {

    fun getMovies(category: String): kotlinx.coroutines.flow.Flow<ApiResult<List<Movie>>> = flow {
        emit(ApiResult.Loading)

        val lastCacheTime = movieDao.getLastCacheTime(category)
        val isCacheExpired = lastCacheTime == null ||
                (System.currentTimeMillis() - lastCacheTime) > PreferencesManager.CACHE_DURATION_MS

        if (!isCacheExpired) {
            movieDao.getMoviesByCategory(category)
                .map { entities -> ApiResult.Success(entities.map { it.toMovie() }) }
                .collect { emit(it) }
            return@flow
        }

        try {
            val language = preferencesManager.selectedLanguage
            val response = when (category) {
                "now_playing" -> apiService.getNowPlayingMovies(
                    apiKey = BuildConfig.TMDB_API_KEY,
                    language = language
                )
                else -> apiService.getPopularMovies(
                    apiKey = BuildConfig.TMDB_API_KEY,
                    language = language
                )
            }

            val entities = response.results.map { tmdbMovie ->
                MovieEntity(
                    id = tmdbMovie.id,
                    title = tmdbMovie.title,
                    overview = tmdbMovie.overview,
                    posterPath = tmdbMovie.posterPath,
                    releaseDate = tmdbMovie.releaseDate,
                    voteAverage = tmdbMovie.voteAverage,
                    category = category,
                    cachedAt = System.currentTimeMillis()
                )
            }
            movieDao.deleteMoviesByCategory(category)
            movieDao.insertMovies(entities)

            movieDao.getMoviesByCategory(category)
                .map { dbEntities -> ApiResult.Success(dbEntities.map { it.toMovie() }) }
                .collect { emit(it) }

        } catch (e: Exception) {
            val cachedMovies = movieDao.getLastCacheTime(category)
            if (cachedMovies != null) {
                movieDao.getMoviesByCategory(category)
                    .map { entities -> ApiResult.Success(entities.map { it.toMovie() }) }
                    .collect { emit(it) }
            } else {
                emit(ApiResult.Error(e.message ?: "Terjadi kesalahan"))
            }
        }
    }

    suspend fun clearCache(category: String) {
        movieDao.deleteMoviesByCategory(category)
    }
}

fun MovieEntity.toMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        posterUrl = if (posterPath != null)
            "${BuildConfig.TMDB_IMAGE_BASE_URL}$posterPath"
        else ""
    )
}