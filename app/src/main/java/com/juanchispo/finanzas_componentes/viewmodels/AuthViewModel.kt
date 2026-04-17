package com.juanchispo.finanzas_componentes

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading

            delay(2000) // Simula login

            if (email == "admin@gmail.com" && password == "1234") {
                _uiState.value = AuthUiState.Success
            } else {
                _uiState.value = AuthUiState.Error("Credenciales incorrectas")
            }
        }
    }
}