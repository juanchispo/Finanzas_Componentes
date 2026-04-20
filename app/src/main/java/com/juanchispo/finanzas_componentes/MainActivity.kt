package com.juanchispo.finanzas_componentes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.juanchispo.finanzas_componentes.navigation.AppNavGraph
import com.juanchispo.finanzas_componentes.ui.theme.Finanzas_ComponentesTheme
import com.juanchispo.finanzas_componentes.viewmodels.LanguageViewModel
import com.juanchispo.finanzas_componentes.viewmodels.UserViewModel

class MainActivity : ComponentActivity() {

    // ViewModels con scope de Activity para que sobrevivan la navegación completa
    private val languageViewModel: LanguageViewModel by viewModels()
    private val userViewModel    : UserViewModel     by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Finanzas_ComponentesTheme {
                AppNavGraph(
                    languageViewModel = languageViewModel,
                    userViewModel     = userViewModel
                )
            }
        }
    }
}