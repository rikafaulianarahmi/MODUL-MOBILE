package com.example.modul3compose.data.remote

import com.example.modul3compose.data.model.TmdbResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "id-ID",
        @Query("page") page: Int = 1
    ): TmdbResponse

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "id-ID",
        @Query("page") page: Int = 1
    ): TmdbResponse
}