package com.example.modul3compose

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.modul3compose.data.model.Movie
import com.example.modul3compose.util.ApiResult
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    viewModel: MovieViewModel,
    onDetailClick: (Movie) -> Unit,
    onWebClick: (Movie) -> Unit,
    onNavigateToLanguage: () -> Unit
) {
    val moviesState by viewModel.moviesState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name), fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = onNavigateToLanguage) {
                        Icon(Icons.Default.Language, contentDescription = "Lang")
                    }
                }
            )
        }
    ) { padding ->
        when (val state = moviesState) {
            is ApiResult.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is ApiResult.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("❌ ${state.message}", color = MaterialTheme.colorScheme.error)
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(onClick = { viewModel.loadMovies() }) { Text("Coba Lagi") }
                    }
                }
            }

            is ApiResult.Success -> {
                LazyColumn(
                    modifier = Modifier.padding(padding).fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        Text("Highlight Movies", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        LazyRow(
                            modifier = Modifier.padding(top = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(state.data.take(5)) { movie ->
                                AsyncImage(
                                    model = movie.posterUrl,
                                    contentDescription = movie.title,
                                    modifier = Modifier
                                        .size(260.dp, 150.dp)
                                        .clip(RoundedCornerShape(16.dp))
                                        .clickable { onDetailClick(movie) },
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("All Movies", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }

                    items(state.data) { movie ->
                        MovieItem(
                            movie = movie,
                            onWebClick = { onWebClick(movie) },
                            onDetailClick = { onDetailClick(movie) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DetailScreen(movie: Movie) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = movie.posterUrl,
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = movie.title, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(
            text = "${stringResource(R.string.label_year)} ${movie.releaseDate.take(4)}",
            fontSize = 16.sp
        )
        Text(
            text = "${stringResource(R.string.label_theme)} ⭐ ${String.format("%.1f", movie.voteAverage)}",
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = movie.overview.ifEmpty { "Sinopsis belum tersedia untuk bahasa ini. Coba ganti bahasa ke English di menu pengaturan bahasa." },
            fontSize = 14.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun LanguageScreen(viewModel: MovieViewModel) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(R.string.title_language), fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = {
            viewModel.setLanguage("id-ID")
            updateLocale(context, "in")
        }) { Text("Bahasa Indonesia") }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            viewModel.setLanguage("en-US")
            updateLocale(context, "en")
        }) { Text("English") }
    }
}

fun updateLocale(context: Context, lang: String) {
    val locale = Locale(lang)
    Locale.setDefault(locale)
    val config = context.resources.configuration
    config.setLocale(locale)
    context.resources.updateConfiguration(config, context.resources.displayMetrics)
    (context as? Activity)?.recreate()
}