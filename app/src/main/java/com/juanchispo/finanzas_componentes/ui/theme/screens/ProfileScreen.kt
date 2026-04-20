package com.juanchispo.finanzas_componentes.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.juanchispo.finanzas_componentes.language.AppLanguage
import com.juanchispo.finanzas_componentes.language.LocalStrings
import com.juanchispo.finanzas_componentes.viewmodels.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    userViewModel    : UserViewModel,
    currentLanguage  : AppLanguage,
    onLanguageChange : (AppLanguage) -> Unit,
    onBack           : () -> Unit = {},
    onLogout         : () -> Unit = {}
) {
    val strings   = LocalStrings.current
    val userState by userViewModel.userState.collectAsState()

    var showEditDialog     by remember { mutableStateOf(false) }
    var showLanguageDialog by remember { mutableStateOf(false) }
    var nameInput          by remember { mutableStateOf(userState.name) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(strings.profileTitle, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor    = Color(0xFF357ABD),
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->

        Column(
            modifier            = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFFF5F5F5)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            // ── Avatar circular ────────────────────────────────────────────
            Box(
                modifier         = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF357ABD)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text       = userState.name.firstOrNull()?.uppercase() ?: "U",
                    color      = Color.White,
                    fontSize   = 36.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text       = userState.name,
                fontSize   = 20.sp,
                fontWeight = FontWeight.Bold,
                color      = Color(0xFF222222)
            )
            Text(
                text     = userState.email,
                fontSize = 14.sp,
                color    = Color.Gray
            )

            Spacer(modifier = Modifier.height(32.dp))

            // ── Tarjeta de opciones ────────────────────────────────────────
            Card(
                modifier  = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                shape     = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                colors    = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column {
                    ProfileOptionItem(
                        icon    = Icons.Default.Edit,
                        label   = strings.editNameButton,
                        color   = Color(0xFF357ABD),
                        onClick = {
                            nameInput = userState.name
                            showEditDialog = true
                        }
                    )
                    HorizontalDivider(color = Color(0xFFEEEEEE))

                    ProfileOptionItem(
                        icon    = Icons.Default.Language,
                        label   = strings.changeLanguage,
                        color   = Color(0xFF4CAF50),
                        onClick = { showLanguageDialog = true }
                    )
                    HorizontalDivider(color = Color(0xFFEEEEEE))

                    ProfileOptionItem(
                        icon    = Icons.Default.ExitToApp,
                        label   = strings.signOutButton,
                        color   = Color(0xFFE53935),
                        onClick = {
                            userViewModel.logout()
                            onLogout()
                        }
                    )
                }
            }
        }
    }

    // ── Diálogo: Editar Nombre ─────────────────────────────────────────────
    if (showEditDialog) {
        AlertDialog(
            onDismissRequest = { showEditDialog = false },
            title  = { Text(strings.editNameButton, fontWeight = FontWeight.Bold) },
            text   = {
                OutlinedTextField(
                    value         = nameInput,
                    onValueChange = { nameInput = it },
                    label         = { Text(strings.nameLabel) },
                    placeholder   = { Text(strings.namePlaceholder) },
                    singleLine    = true,
                    modifier      = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        userViewModel.updateName(nameInput)
                        showEditDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF357ABD))
                ) { Text(strings.saveLabel, color = Color.White) }
            },
            dismissButton = {
                OutlinedButton(onClick = { showEditDialog = false }) {
                    Text(strings.cancelLabel)
                }
            }
        )
    }

    // ── Diálogo: Cambiar Idioma ────────────────────────────────────────────
    if (showLanguageDialog) {
        AlertDialog(
            onDismissRequest = { showLanguageDialog = false },
            title  = { Text(strings.selectLanguage, fontWeight = FontWeight.Bold) },
            text   = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    LanguageOption(
                        label     = "🇲🇽  ${strings.spanish}",
                        selected  = currentLanguage == AppLanguage.ES,
                        onClick   = {
                            onLanguageChange(AppLanguage.ES)
                            showLanguageDialog = false
                        }
                    )
                    LanguageOption(
                        label     = "🇺🇸  ${strings.english}",
                        selected  = currentLanguage == AppLanguage.EN,
                        onClick   = {
                            onLanguageChange(AppLanguage.EN)
                            showLanguageDialog = false
                        }
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { showLanguageDialog = false }) {
                    Text(strings.cancelLabel)
                }
            }
        )
    }
}

@Composable
private fun ProfileOptionItem(
    icon   : ImageVector,
    label  : String,
    color  : Color,
    onClick: () -> Unit
) {
    Row(
        modifier            = Modifier
            .fillMaxWidth()
            .padding(0.dp),
        verticalAlignment   = Alignment.CenterVertically
    ) {
        TextButton(
            onClick  = onClick,
            modifier = Modifier.fillMaxWidth(),
            shape    = RoundedCornerShape(0.dp)
        ) {
            Row(
                modifier            = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment   = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    imageVector        = icon,
                    contentDescription = null,
                    tint               = color,
                    modifier           = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text       = label,
                    color      = Color(0xFF222222),
                    fontSize   = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
private fun LanguageOption(label: String, selected: Boolean, onClick: () -> Unit) {
    Row(
        modifier          = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick  = onClick,
            colors   = RadioButtonDefaults.colors(selectedColor = Color(0xFF357ABD))
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = label, fontSize = 16.sp)
    }
}
