package com.juanchispo.finanzas_componentes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.juanchispo.finanzas_componentes.ui.theme.Finanzas_ComponentesTheme
import com.juanchispo.finanzas_componentes.ui.theme.screens.LoginScreen
import com.juanchispo.finanzas_componentes.ui.theme.screens.AddExpenseScreen
import com.juanchispo.finanzas_componentes.ui.theme.screens.HistoryScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Finanzas_ComponentesTheme {
                //LoginScreen()
                //AddExpenseScreen() esta es la view de ver el gasto
                HistoryScreen()
            }
        }
    }
}