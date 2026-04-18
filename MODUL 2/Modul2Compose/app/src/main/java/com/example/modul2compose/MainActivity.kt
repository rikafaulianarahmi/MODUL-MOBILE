package com.example.modul2compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale
import kotlin.math.ceil

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipCalculatorApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipCalculatorApp() {
    var amountInput by remember { mutableStateOf("") }
    var tipPercentage by remember { mutableIntStateOf(15) }
    var roundUp by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    val options = listOf(15, 18, 20)

    val amount = amountInput.toDoubleOrNull() ?: 0.0
    var tip = amount * (tipPercentage / 100.0)
    if (roundUp) {
        tip = ceil(tip)
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 32.dp, vertical = 48.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "Calculate Tip",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
        
        TextField(
            value = amountInput,
            onValueChange = { amountInput = it },
            label = { Text("Bill Amount") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                value = "$tipPercentage%",
                onValueChange = {},
                readOnly = true,
                label = { Text("Tip Percentage") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                modifier = Modifier
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                    .fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text("$selectionOption%") },
                        onClick = {
                            tipPercentage = selectionOption
                            expanded = false
                        }
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .size(48.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Round up tip?")
            Switch(
                checked = roundUp,
                onCheckedChange = { roundUp = it }
            )
        }

        Column(modifier = Modifier.padding(top = 16.dp)) {
            Text(
                text = "Tip Amount:",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "$${String.format(Locale.US, "%.2f", tip)}",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}