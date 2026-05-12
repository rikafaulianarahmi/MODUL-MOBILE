package com.example.modul3compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LegoItem(lego: Lego, onWebClick: () -> Unit, onDetailClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2C2C2E)),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
            Image(
                painter = painterResource(id = lego.imageRes),
                contentDescription = null,
                modifier = Modifier.size(100.dp, 140.dp).clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(start = 16.dp).weight(1f)) {
                // Baris 1: Judul & Tahun
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = lego.title, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f), maxLines = 1, overflow = TextOverflow.Ellipsis)
                    Text(text = lego.year, color = Color.LightGray, fontSize = 14.sp)
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Baris 2: Tema & Deskripsi
                Row {
                    Text(text = stringResource(R.string.label_theme), color = Color.LightGray, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    Text(text = " ${lego.theme}", color = Color.LightGray, fontSize = 13.sp)
                }
                Text(text = lego.description, color = Color.Gray, fontSize = 12.sp, maxLines = 2, overflow = TextOverflow.Ellipsis, modifier = Modifier.padding(top = 4.dp))

                Spacer(modifier = Modifier.height(12.dp))

                // Baris 3: Tombol
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onWebClick) {
                        Text(stringResource(R.string.btn_web), color = Color(0xFF98A7F5))
                    }
                    Button(onClick = onDetailClick, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF98A7F5))) {
                        Text(stringResource(R.string.btn_detail), color = Color.Black)
                    }
                }
            }
        }
    }
}