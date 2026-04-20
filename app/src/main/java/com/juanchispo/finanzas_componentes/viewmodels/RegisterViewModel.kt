package com.juanchispo.finanzas_componentes.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

sealed class RegisterUiState {
    object Idle    : RegisterUiState()
    object Loading : RegisterUiState()
    object Success : RegisterUiState()
    data class Error(val message: String) : RegisterUiState()
}

class RegisterViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<RegisterUiState>(RegisterUiState.Idle)
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun register(email: String, password: String, confirmPassword: String, nombre: String) {
        if (email.isBlank() || password.isBlank() || nombre.isBlank()) {
            _uiState.value = RegisterUiState.Error("Todos los campos son obligatorios")
            return
        }
        if (password != confirmPassword) {
            _uiState.value = RegisterUiState.Error("Las contraseñas no coinciden")
            return
        }

        viewModelScope.launch {
            _uiState.value = RegisterUiState.Loading
            delay(1500) // Simula llamada al backend
            _uiState.value = RegisterUiState.Success
        }
    }

    fun resetState() {
        _uiState.value = RegisterUiState.Idle
    }
}