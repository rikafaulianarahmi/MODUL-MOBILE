package com.example.modul3compose

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.modul3compose.ui.theme.Modul3composeTheme
import timber.log.Timber

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val app = application as MyApplication
        val viewModel = ViewModelProvider(
            this,
            MovieViewModel.Factory(app.movieRepository, app.preferencesManager)
        )[MovieViewModel::class.java]

        setContent {
            Modul3composeTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {

                    val navController = rememberNavController()
                    val clickEvent by viewModel.clickEvent.collectAsState()

                    LaunchedEffect(clickEvent) {
                        when (val event = clickEvent) {
                            is MovieViewModel.ClickEvent.DetailClick -> {
                                Timber.d("Berpindah ke Detail → ${event.movie.title}")
                                navController.navigate("detail_screen/${event.movie.id}")
                                viewModel.resetClickEvent()
                            }
                            is MovieViewModel.ClickEvent.WebClick -> {
                                val url = "https://www.themoviedb.org/movie/${event.movie.id}"
                                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                                viewModel.resetClickEvent()
                            }
                            null -> {}
                        }
                    }

                    NavHost(navController = navController, startDestination = "list_screen") {
                        composable("list_screen") {
                            ListScreen(
                                viewModel = viewModel,
                                onDetailClick = { movie -> viewModel.onDetailClick(movie) },
                                onWebClick = { movie -> viewModel.onWebClick(movie) },
                                onNavigateToLanguage = { navController.navigate("language_screen") }
                            )
                        }
                        composable("detail_screen/{movieId}") { backStackEntry ->
                            val id = backStackEntry.arguments?.getString("movieId")?.toIntOrNull()
                            val moviesState = viewModel.moviesState.collectAsState()
                            val movies = (moviesState.value as? com.example.modul3compose.util.ApiResult.Success)?.data
                            val selected = movies?.find { it.id == id } ?: viewModel.selectedMovie.collectAsState().value
                            if (selected != null) DetailScreen(movie = selected)
                        }
                        composable("language_screen") {
                            LanguageScreen(viewModel = viewModel)
                        }
                    }
                }
            }
        }
    }
}