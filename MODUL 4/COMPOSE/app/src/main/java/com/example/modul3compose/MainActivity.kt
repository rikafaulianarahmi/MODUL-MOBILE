package com.example.modul3compose

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import timber.log.Timber

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {

                    val navController = rememberNavController()

                    val factory = LegoViewModelFactory("Aplikasi Lego Compose")
                    val viewModel: LegoViewModel = viewModel(factory = factory)

                    // Collect StateFlow
                    val legoList by viewModel.legoList.collectAsState()
                    val clickEvent by viewModel.clickEvent.collectAsState()

                    // Handle event klik from ViewModel
                    LaunchedEffect(clickEvent) {
                        when (val event = clickEvent) {
                            is LegoViewModel.ClickEvent.DetailClick -> {
                                Timber.d("Berpindah ke Detail → ID: ${event.lego.id} | Judul: ${event.lego.title}")
                                navController.navigate("detail_screen/${event.lego.id}")
                                viewModel.resetClickEvent()
                            }
                            is LegoViewModel.ClickEvent.WebClick -> {
                                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(event.lego.webUrl)))
                                viewModel.resetClickEvent()
                            }
                            null -> {}
                        }
                    }

                    NavHost(navController = navController, startDestination = "list_screen") {
                        composable("list_screen") {
                            ListScreen(
                                legoList = legoList,
                                onDetailClick = { lego -> viewModel.onDetailClick(lego) },
                                onWebClick = { lego -> viewModel.onWebClick(lego) },
                                onNavigateToLanguage = { navController.navigate("language_screen") }
                            )
                        }

                        composable("detail_screen/{legoId}") { backStackEntry ->
                            val id = backStackEntry.arguments?.getString("legoId")?.toIntOrNull()
                            val selectedLego = legoList.find { it.id == id }
                            if (selectedLego != null) {
                                DetailScreen(lego = selectedLego)
                            }
                        }

                        composable("language_screen") {
                            LanguageScreen()
                        }
                    }
                }
            }
        }
    }
}