package com.juanchispo.finanzas_componentes.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class UserState(
    val name : String = "Juan Chispo",
    val email: String = "admin@gmail.com"
)

class UserViewModel : ViewModel() {
    private val _userState = MutableStateFlow(UserState())
    val userState: StateFlow<UserState> = _userState.asStateFlow()

    fun updateName(newName: String) {
        if (newName.isNotBlank()) {
            _userState.value = _userState.value.copy(name = newName)
        }
    }

    fun logout() {
        // Resetea el estado al cerrar sesión (el backend lo manejará luego)
        _userState.value = UserState()
    }
}
