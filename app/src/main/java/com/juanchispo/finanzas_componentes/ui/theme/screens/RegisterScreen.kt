package com.juanchispo.finanzas_componentes.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.juanchispo.finanzas_componentes.language.LocalStrings
import com.juanchispo.finanzas_componentes.viewmodels.RegisterUiState
import com.juanchispo.finanzas_componentes.viewmodels.RegisterViewModel

@Composable
fun RegisterScreen(
    onRegisterSuccess : () -> Unit = {},
    onNavigateToLogin : () -> Unit = {},
    viewModel         : RegisterViewModel = viewModel()
) {
    val strings = LocalStrings.current
    val uiState by viewModel.uiState.collectAsState()

    var email           by remember { mutableStateOf("") }
    var password        by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var nombre          by remember { mutableStateOf("") }

    LaunchedEffect(uiState) {
        if (uiState is RegisterUiState.Success) {
            onRegisterSuccess()
            viewModel.resetState()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize().background(color = Color(0xFF357ABD))
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            Row(
                modifier          = Modifier.fillMaxWidth().statusBarsPadding().padding(horizontal = 8.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onNavigateToLogin) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = Color.White)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(strings.appName, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier  = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                shape     = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors    = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    Text(strings.emailLabel, fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(value = email, onValueChange = { email = it },
                        placeholder = { Text(strings.emailHint, color = Color.LightGray) },
                        modifier = Modifier.fillMaxWidth(), singleLine = true)

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(strings.passwordLabel, fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(value = password, onValueChange = { password = it },
                        placeholder = { Text(strings.choosePasswordHint, color = Color.LightGray) },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(), singleLine = true)

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(strings.confirmPasswordLabel, fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(value = confirmPassword, onValueChange = { confirmPassword = it },
                        placeholder = { Text(strings.confirmPasswordHint, color = Color.LightGray) },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(), singleLine = true)

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(strings.fullNameLabel, fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(value = nombre, onValueChange = { nombre = it },
                        placeholder = { Text(strings.fullNameHint, color = Color.LightGray) },
                        modifier = Modifier.fillMaxWidth(), singleLine = true)

                    Spacer(modifier = Modifier.height(20.dp))

                    if (uiState is RegisterUiState.Error) {
                        val msg = when ((uiState as RegisterUiState.Error).message) {
                            "Todos los campos son obligatorios" -> strings.allRequired
                            "Las contraseñas no coinciden"      -> strings.passwordMismatch
                            else                                -> (uiState as RegisterUiState.Error).message
                        }
                        Text(msg, color = Color.Red, fontSize = 13.sp, modifier = Modifier.padding(bottom = 8.dp))
                    }

                    Button(
                        onClick  = { viewModel.register(email, password, confirmPassword, nombre) },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        enabled  = uiState !is RegisterUiState.Loading,
                        colors   = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                        shape    = RoundedCornerShape(8.dp)
                    ) {
                        if (uiState is RegisterUiState.Loading) {
                            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
                        } else {
                            Text(strings.signUpButton, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(strings.alreadyAccount, color = Color.White)
                TextButton(onClick = onNavigateToLogin, contentPadding = PaddingValues(0.dp)) {
                    Text(strings.signInLink, color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }

        if (uiState is RegisterUiState.Loading) {
            Box(
                modifier         = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) { CircularProgressIndicator() }
        }
    }
}