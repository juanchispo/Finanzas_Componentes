package com.juanchispo.finanzas_componentes.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.juanchispo.finanzas_componentes.viewmodels.GastoResumen
import com.juanchispo.finanzas_componentes.viewmodels.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToAddExpense : () -> Unit = {},
    onNavigateToHistory    : () -> Unit = {},
    onNavigateToBudget     : () -> Unit = {},
    viewModel              : HomeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Resumen",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /* menú lateral futuro */ }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menú",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor    = Color(0xFF357ABD),
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = true,
                    onClick  = {},
                    icon     = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
                    label    = { Text("Inicio") },
                    colors   = NavigationBarItemDefaults.colors(
                        indicatorColor   = Color(0xFF357ABD).copy(alpha = 0.15f),
                        selectedIconColor = Color(0xFF357ABD),
                        selectedTextColor = Color(0xFF357ABD)
                    )
                )
                NavigationBarItem(
                    selected = false,
                    onClick  = onNavigateToHistory,
                    icon     = { Icon(Icons.Default.History, contentDescription = "Historial") },
                    label    = { Text("Historial") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick  = onNavigateToBudget,
                    icon     = { Icon(Icons.Default.AccountBalance, contentDescription = "Presupuesto") },
                    label    = { Text("Presupuesto") }
                )
            }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick              = onNavigateToAddExpense,
                containerColor       = Color(0xFF4CAF50),
                contentColor         = Color.White,
                shape                = RoundedCornerShape(12.dp),
                modifier             = Modifier
                    .fillMaxWidth(0.9f)
                    .height(52.dp),
                icon  = { Icon(Icons.Default.Add, contentDescription = null) },
                text  = { Text("Añadir Gasto", fontWeight = FontWeight.Bold, fontSize = 16.sp) }
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFFF5F5F5)),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // ── Tarjeta resumen ────────────────────────────────────────────
            item {
                Card(
                    modifier  = Modifier.fillMaxWidth(),
                    shape     = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors    = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text  = "Gasto del Mes:",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text  = "$${"%.0f".format(uiState.gastoDelMes)}",
                                color = Color(0xFF357ABD),
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text  = "Presupuesto:",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text  = "$${"%.0f".format(uiState.presupuesto)}",
                                color = Color.Gray,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        LinearProgressIndicator(
                            progress      = { uiState.porcentajeUsado },
                            modifier      = Modifier
                                .fillMaxWidth()
                                .height(10.dp),
                            color         = Color(0xFF4CAF50),
                            trackColor    = Color(0xFFDDDDDD)
                        )
                    }
                }
            }

            // ── Encabezado últimos gastos ──────────────────────────────────
            item {
                Text(
                    text       = "Últimos Gastos",
                    fontWeight = FontWeight.Bold,
                    fontSize   = 18.sp,
                    modifier   = Modifier.padding(horizontal = 4.dp)
                )
            }

            // ── Lista de últimos gastos ────────────────────────────────────
            items(uiState.ultimosGastos) { gasto ->
                UltimoGastoItem(gasto)
            }

            // Espacio extra para que el FAB no tape el último elemento
            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
    }
}

@Composable
private fun UltimoGastoItem(gasto: GastoResumen) {
    Card(
        modifier  = Modifier.fillMaxWidth(),
        shape     = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors    = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment     = Alignment.CenterVertically
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text       = "${gasto.categoria}:",
                    fontWeight = FontWeight.Medium,
                    fontSize   = 15.sp
                )
                Text(
                    text       = "$${"%.2f".format(gasto.monto)}",
                    color      = Color(0xFF2E7D32),
                    fontWeight = FontWeight.Bold,
                    fontSize   = 15.sp
                )
            }
            Text(
                text  = gasto.fecha,
                color = Color.Gray,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
