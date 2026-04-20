package com.juanchispo.finanzas_componentes.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.juanchispo.finanzas_componentes.language.LocalStrings
import com.juanchispo.finanzas_componentes.language.getStrings
import com.juanchispo.finanzas_componentes.ui.theme.screens.*
import com.juanchispo.finanzas_componentes.viewmodels.LanguageViewModel
import com.juanchispo.finanzas_componentes.viewmodels.UserViewModel

@Composable
fun AppNavGraph(
    languageViewModel: LanguageViewModel,
    userViewModel    : UserViewModel
) {
    val language by languageViewModel.language.collectAsState()
    val strings  = getStrings(language)

    // Provee los strings del idioma actual a TODA la jerarquía de composables
    CompositionLocalProvider(LocalStrings provides strings) {

        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = AppRoutes.LOGIN) {

            // ── Login ──────────────────────────────────────────────────────
            composable(AppRoutes.LOGIN) {
                LoginScreen(
                    onLoginSuccess       = {
                        navController.navigate(AppRoutes.HOME) {
                            popUpTo(AppRoutes.LOGIN) { inclusive = true }
                        }
                    },
                    onNavigateToRegister = { navController.navigate(AppRoutes.REGISTER) },
                    currentLanguage      = language,
                    onLanguageChange     = { languageViewModel.setLanguage(it) }
                )
            }

            // ── Registro ───────────────────────────────────────────────────
            composable(AppRoutes.REGISTER) {
                RegisterScreen(
                    onRegisterSuccess = {
                        navController.navigate(AppRoutes.HOME) {
                            popUpTo(AppRoutes.LOGIN) { inclusive = true }
                        }
                    },
                    onNavigateToLogin = { navController.popBackStack() }
                )
            }

            // ── Home / Resumen ─────────────────────────────────────────────
            composable(AppRoutes.HOME) {
                HomeScreen(
                    onNavigateToAddExpense = { navController.navigate(AppRoutes.ADD_EXPENSE) },
                    onNavigateToHistory    = { navController.navigate(AppRoutes.HISTORY) },
                    onNavigateToBudget     = { navController.navigate(AppRoutes.BUDGET) },
                    onNavigateToProfile    = { navController.navigate(AppRoutes.PROFILE) }
                )
            }

            // ── Historial ──────────────────────────────────────────────────
            composable(AppRoutes.HISTORY) {
                HistoryScreen(onBack = { navController.popBackStack() })
            }

            // ── Agregar Gasto ──────────────────────────────────────────────
            composable(AppRoutes.ADD_EXPENSE) {
                AddExpenseScreen(onBack = { navController.popBackStack() })
            }

            // ── Presupuesto ────────────────────────────────────────────────
            composable(AppRoutes.BUDGET) {
                BudgetScreen(onBack = { navController.popBackStack() })
            }

            // ── Perfil ─────────────────────────────────────────────────────
            composable(AppRoutes.PROFILE) {
                ProfileScreen(
                    userViewModel    = userViewModel,
                    currentLanguage  = language,
                    onLanguageChange = { languageViewModel.setLanguage(it) },
                    onBack           = { navController.popBackStack() },
                    onLogout         = {
                        navController.navigate(AppRoutes.LOGIN) {
                            popUpTo(AppRoutes.HOME) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}
