package com.example.modul3compose

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    legoList: List<Lego>,
    onDetailClick: (Lego) -> Unit,
    onWebClick: (Lego) -> Unit,
    onNavigateToLanguage: () -> Unit
) {
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
        LazyColumn(
            modifier = Modifier.padding(padding).fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(stringResource(R.string.title_highlight), fontWeight = FontWeight.Bold, fontSize = 18.sp)
                LazyRow(modifier = Modifier.padding(top = 8.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(legoList) { lego ->
                        Image(
                            painter = painterResource(lego.imageRes), contentDescription = null,
                            modifier = Modifier.size(260.dp, 150.dp).clip(RoundedCornerShape(16.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(stringResource(R.string.title_all_items), fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }

            items(legoList) { lego ->
                LegoItem(
                    lego = lego,
                    onWebClick = { onWebClick(lego) },
                    onDetailClick = { onDetailClick(lego) }
                )
            }
        }
    }
}

@Composable
fun DetailScreen(lego: Lego) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(lego.imageRes), contentDescription = null, modifier = Modifier.size(200.dp).clip(RoundedCornerShape(16.dp)), contentScale = ContentScale.Crop)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = lego.title, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(text = "${stringResource(R.string.label_year)} ${lego.year}", fontSize = 16.sp)
        Text(text = "${stringResource(R.string.label_theme)} ${lego.theme}", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = lego.description, fontSize = 14.sp)
    }
}

@Composable
fun LanguageScreen() {
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(stringResource(R.string.title_language), fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { updateLocale(context, "in") }) { Text("Bahasa Indonesia") }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { updateLocale(context, "en") }) { Text("English") }
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