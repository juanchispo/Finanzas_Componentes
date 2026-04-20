package com.juanchispo.finanzas_componentes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.juanchispo.finanzas_componentes.navigation.AppNavGraph
import com.juanchispo.finanzas_componentes.ui.theme.Finanzas_ComponentesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Finanzas_ComponentesTheme {
                AppNavGraph()
            }
        }
    }
}