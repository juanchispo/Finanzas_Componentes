package com.juanchispo.finanzas_componentes.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.juanchispo.finanzas_componentes.AuthUiState
import com.juanchispo.finanzas_componentes.AuthViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LoginScreen(
    onLoginSuccess      : () -> Unit = {},
    onNavigateToRegister: () -> Unit = {},
    viewModel           : AuthViewModel = viewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    var email    by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Navegar al Home cuando el login sea exitoso
    LaunchedEffect(uiState) {
        if (uiState is AuthUiState.Success) {
            onLoginSuccess()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF4A90E2),
                        Color(0xFF357ABD)
                    )
                )
            )
    ) {

        Column(
            modifier              = Modifier.fillMaxSize(),
            horizontalAlignment   = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(60.dp))

            Text(
                text       = "SmartExpense",
                color      = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize   = 26.sp
            )

            Spacer(modifier = Modifier.height(40.dp))

            Card(
                modifier  = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                shape     = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors    = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    Text("Correo Electrónico", fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value         = email,
                        onValueChange = { email = it },
                        placeholder   = { Text("Ingresa tu correo", color = Color.LightGray) },
                        modifier      = Modifier.fillMaxWidth(),
                        singleLine    = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text("Contraseña", fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value                = password,
                        onValueChange        = { password = it },
                        placeholder          = { Text("Ingresa tu contraseña", color = Color.LightGray) },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier             = Modifier.fillMaxWidth(),
                        singleLine           = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick  = { viewModel.login(email, password) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        enabled = uiState !is AuthUiState.Loading,
                        colors  = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF357ABD)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        if (uiState is AuthUiState.Loading) {
                            CircularProgressIndicator(
                                color       = Color.White,
                                modifier    = Modifier.size(24.dp),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(
                                "Iniciar Sesión",
                                color      = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize   = 16.sp
                            )
                        }
                    }

                    if (uiState is AuthUiState.Error) {
                        Text(
                            text     = (uiState as AuthUiState.Error).message,
                            color    = Color.Red,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ── Link "¿No tienes cuenta?" ──────────────────────────────────
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "¿No tienes cuenta? ", color = Color.White)
                TextButton(
                    onClick        = onNavigateToRegister,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text       = "Regístrate",
                        color      = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Overlay de loading
        if (uiState is AuthUiState.Loading) {
            Box(
                modifier         = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}
