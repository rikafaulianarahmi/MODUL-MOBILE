package com.example.modul3compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val legoList = getDummyLegoList()

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {

                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "list_screen") {

                        composable("list_screen") {
                            ListScreen(
                                legoList = legoList,
                                onNavigateToDetail = { id -> navController.navigate("detail_screen/$id") },
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