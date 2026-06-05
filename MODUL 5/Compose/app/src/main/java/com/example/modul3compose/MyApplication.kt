package com.example.modul3compose

import android.app.Application
import com.example.modul3compose.data.local.MovieDatabase
import com.example.modul3compose.data.remote.NetworkClient
import com.example.modul3compose.data.repository.MovieRepository
import com.example.modul3compose.util.PreferencesManager

class MyApplication : Application() {

    lateinit var movieRepository: MovieRepository
    lateinit var preferencesManager: PreferencesManager

    override fun onCreate() {
        super.onCreate()

        preferencesManager = PreferencesManager(this)

        val movieDao = MovieDatabase.getInstance(this).movieDao()

        movieRepository = MovieRepository(
            apiService = NetworkClient.tmdbApiService,
            movieDao = movieDao,
            preferencesManager = preferencesManager
        )
    }
}