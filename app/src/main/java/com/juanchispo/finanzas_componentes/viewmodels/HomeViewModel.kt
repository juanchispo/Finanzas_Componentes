package com.juanchispo.finanzas_componentes.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class HomeUiState(
    val gastoDelMes: Double = 1200.0,
    val presupuesto: Double = 2000.0,
    val ultimosGastos: List<GastoResumen> = listOf(
        GastoResumen("Comida",          25.0,  "24 Abr 2024"),
        GastoResumen("Transporte",      50.0,  "23 Abr 2024"),
        GastoResumen("Entretenimiento", 30.0,  "23 Abr 2024")
    )
) {
    /** Porcentaje del presupuesto ya utilizado (0f – 1f) */
    val porcentajeUsado: Float
        get() = if (presupuesto > 0) (gastoDelMes / presupuesto).toFloat() else 0f
}

data class GastoResumen(
    val categoria: String,
    val monto: Double,
    val fecha: String
)

class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
}