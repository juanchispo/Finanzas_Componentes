package com.juanchispo.finanzas_componentes.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.juanchispo.finanzas_componentes.ui.theme.screens.AddExpenseScreen
import com.juanchispo.finanzas_componentes.ui.theme.screens.BudgetScreen
import com.juanchispo.finanzas_componentes.ui.theme.screens.HistoryScreen
import com.juanchispo.finanzas_componentes.ui.theme.screens.HomeScreen
import com.juanchispo.finanzas_componentes.ui.theme.screens.LoginScreen
import com.juanchispo.finanzas_componentes.ui.theme.screens.RegisterScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController  = navController,
        startDestination = AppRoutes.LOGIN
    ) {

        // ── Login ──────────────────────────────────────────────────────────
        composable(AppRoutes.LOGIN) {
            LoginScreen(
                onLoginSuccess       = {
                    navController.navigate(AppRoutes.HOME) {
                        // Limpia el back-stack para que "atrás" no regrese al login
                        popUpTo(AppRoutes.LOGIN) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(AppRoutes.REGISTER)
                }
            )
        }

        // ── Registro ───────────────────────────────────────────────────────
        composable(AppRoutes.REGISTER) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(AppRoutes.HOME) {
                        popUpTo(AppRoutes.LOGIN) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }

        // ── Home / Resumen ─────────────────────────────────────────────────
        composable(AppRoutes.HOME) {
            HomeScreen(
                onNavigateToAddExpense = {
                    navController.navigate(AppRoutes.ADD_EXPENSE)
                },
                onNavigateToHistory   = {
                    navController.navigate(AppRoutes.HISTORY)
                },
                onNavigateToBudget    = {
                    navController.navigate(AppRoutes.BUDGET)
                }
            )
        }

        // ── Historial de Gastos ────────────────────────────────────────────
        composable(AppRoutes.HISTORY) {
            HistoryScreen(
                onBack = { navController.popBackStack() }
            )
        }

        // ── Agregar Gasto ──────────────────────────────────────────────────
        composable(AppRoutes.ADD_EXPENSE) {
            AddExpenseScreen(
                onBack = { navController.popBackStack() }
            )
        }

        // ── Presupuesto ────────────────────────────────────────────────────
        composable(AppRoutes.BUDGET) {
            BudgetScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
