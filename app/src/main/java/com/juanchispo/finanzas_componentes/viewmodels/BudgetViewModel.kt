package com.juanchispo.finanzas_componentes.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

data class BudgetUiState(
    val presupuestoTotal: Double = 2000.0,
    val gastoActual: Double     = 1200.0
) {
    val porcentajeUsado: Float
        get() = if (presupuestoTotal > 0)
            ((gastoActual / presupuestoTotal) * 100).toFloat() else 0f

    val restante: Double
        get() = presupuestoTotal - gastoActual
}

class BudgetViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(BudgetUiState())
    val uiState: StateFlow<BudgetUiState> = _uiState.asStateFlow()

    fun actualizarPresupuesto(nuevo: Double) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(presupuestoTotal = nuevo)
        }
    }
}
