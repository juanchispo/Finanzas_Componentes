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
import com.juanchispo.finanzas_componentes.language.AppLanguage
import com.juanchispo.finanzas_componentes.language.LocalStrings
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LoginScreen(
    onLoginSuccess      : () -> Unit = {},
    onNavigateToRegister: () -> Unit = {},
    currentLanguage     : AppLanguage,
    onLanguageChange    : (AppLanguage) -> Unit,
    viewModel           : AuthViewModel = viewModel()
) {
    val strings = LocalStrings.current
    val uiState by viewModel.uiState.collectAsState()
    var email    by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(uiState) {
        if (uiState is AuthUiState.Success) onLoginSuccess()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF4A90E2), Color(0xFF357ABD))
                )
            )
    ) {
        // ── Botón de idioma (top-right) ────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding(),
            contentAlignment = Alignment.TopEnd
        ) {
            TextButton(
                onClick        = {
                    val next = if (currentLanguage == AppLanguage.ES) AppLanguage.EN else AppLanguage.ES
                    onLanguageChange(next)
                },
                modifier       = Modifier.padding(end = 12.dp, top = 8.dp),
                colors         = ButtonDefaults.textButtonColors(contentColor = Color.White),
                shape          = RoundedCornerShape(20.dp)
            ) {
                Text(
                    text       = if (currentLanguage == AppLanguage.ES) "EN" else "ES",
                    fontWeight = FontWeight.Bold,
                    fontSize   = 15.sp
                )
            }
        }

        Column(
            modifier            = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(80.dp))

            Text(
                text       = strings.appName,
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

                    Text(strings.emailLabel, fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value         = email,
                        onValueChange = { email = it },
                        placeholder   = { Text(strings.emailHint, color = Color.LightGray) },
                        modifier      = Modifier.fillMaxWidth(),
                        singleLine    = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(strings.passwordLabel, fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value                = password,
                        onValueChange        = { password = it },
                        placeholder          = { Text(strings.passwordHint, color = Color.LightGray) },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier             = Modifier.fillMaxWidth(),
                        singleLine           = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick  = { viewModel.login(email, password) },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        enabled  = uiState !is AuthUiState.Loading,
                        colors   = ButtonDefaults.buttonColors(containerColor = Color(0xFF357ABD)),
                        shape    = RoundedCornerShape(8.dp)
                    ) {
                        if (uiState is AuthUiState.Loading) {
                            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
                        } else {
                            Text(strings.signInButton, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }
                    }

                    if (uiState is AuthUiState.Error) {
                        Text(
                            text     = strings.invalidCredentials,
                            color    = Color.Red,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = strings.noAccount, color = Color.White)
                TextButton(
                    onClick        = onNavigateToRegister,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(strings.signUpLink, color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }

        if (uiState is AuthUiState.Loading) {
            Box(
                modifier         = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) { CircularProgressIndicator() }
        }
    }
}
