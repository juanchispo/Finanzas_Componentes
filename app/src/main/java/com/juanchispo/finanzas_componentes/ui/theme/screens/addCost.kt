package com.juanchispo.finanzas_componentes.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.juanchispo.finanzas_componentes.language.LocalStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreen(onBack: () -> Unit = {}) {
    val strings = LocalStrings.current

    var monto      by remember { mutableStateOf("") }
    var categoria  by remember { mutableStateOf("") }
    var fecha      by remember { mutableStateOf("24/04/2024") }
    var descripcion by remember { mutableStateOf("") }
    var expanded   by remember { mutableStateOf(false) }

    val categorias = listOf(
        strings.catFood, strings.catTransport, strings.catHealth, strings.catLeisure
    )

    // Inicializa la categoría con el primer elemento del idioma actual
    LaunchedEffect(strings) { categoria = categorias.first() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(strings.addExpenseTitle) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor    = Color(0xFF357ABD),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier.padding(padding).padding(16.dp).fillMaxSize()
        ) {
            Text(strings.amountLabel)
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value         = monto,
                onValueChange = { monto = it },
                leadingIcon   = { Text("$") },
                modifier      = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(strings.categoryLabel)
            Spacer(modifier = Modifier.height(4.dp))
            ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
                OutlinedTextField(
                    value         = categoria,
                    onValueChange = {},
                    readOnly      = true,
                    trailingIcon  = { Icon(Icons.Default.KeyboardArrowDown, null) },
                    modifier      = Modifier.fillMaxWidth().menuAnchor()
                )
                ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    categorias.forEach {
                        DropdownMenuItem(
                            text    = { Text(it) },
                            onClick = { categoria = it; expanded = false }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(strings.dateLabel)
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value         = fecha,
                onValueChange = { fecha = it },
                trailingIcon  = { Icon(Icons.Default.DateRange, null) },
                modifier      = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(strings.descriptionLabel)
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value         = descripcion,
                onValueChange = { descripcion = it },
                modifier      = Modifier.fillMaxWidth().height(100.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick  = {},
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors   = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                shape    = RoundedCornerShape(8.dp)
            ) {
                Text(strings.saveButton, color = Color.White)
            }
        }
    }
}