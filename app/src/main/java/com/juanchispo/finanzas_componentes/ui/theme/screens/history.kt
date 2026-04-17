package com.juanchispo.finanzas_componentes.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// 🔹 Modelo de gasto
data class Gasto(
    val categoria: String,
    val monto: Double,
    val fecha: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    onBack: () -> Unit = {}
) {

    val gastos = listOf(
        Gasto("Comida", 25.0, "24 Abr 2026"),
        Gasto("Transporte", 50.0, "23 Abr 2026"),
        Gasto("Renta", 400.0, "20 Abr 2026"),
        Gasto("Cine", 30.0, "18 Abr 2026")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Historial de Gastos") },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF357ABD),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {

            // 🔹 Barra superior (icono + botón filtrar)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Person, contentDescription = null)

                Button(onClick = { /* filtro */ }) {
                    Text("Filtrar")
                }
            }

            // 🔹 Lista de gastos
            LazyColumn {
                items(gastos) { gasto ->
                    GastoItem(gasto)
                    Divider()
                }
            }
        }
    }
}

@Composable
fun GastoItem(gasto: Gasto) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column {
            Row {
                Text(text = "${gasto.categoria}: ")
                Text(
                    text = "$${gasto.monto}",
                    color = Color(0xFF2E7D32) // verde
                )
            }
            Text(text = gasto.fecha, style = MaterialTheme.typography.bodySmall)
        }

        IconButton(onClick = { /* eliminar */ }) {
            Icon(Icons.Default.Delete, contentDescription = "Eliminar")
        }
    }
}