package com.juanchispo.finanzas_componentes.ui.theme.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.juanchispo.finanzas_componentes.viewmodels.BudgetViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetScreen(
    onBack    : () -> Unit = {},
    viewModel : BudgetViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // Anima el arco del gráfico de dona desde 0 hasta el valor real
    val animatedProgress by animateFloatAsState(
        targetValue  = uiState.porcentajeUsado / 100f,
        animationSpec = tween(durationMillis = 900),
        label        = "donut_progress"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text       = "Presupuesto",
                        fontWeight = FontWeight.Bold,
                        fontSize   = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector        = Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint               = Color.White
                        )
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
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFFF5F5F5)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(32.dp))

            // ── Título ─────────────────────────────────────────────────────
            Text(
                text       = "Presupuesto Mensual",
                fontWeight = FontWeight.Bold,
                fontSize   = 22.sp,
                color      = Color(0xFF222222)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // ── Gráfico de dona ────────────────────────────────────────────
            Box(
                contentAlignment = Alignment.Center,
                modifier         = Modifier.size(200.dp)
            ) {
                DonutChart(
                    progress     = animatedProgress,
                    usedColor    = Color(0xFF357ABD),
                    remainColor  = Color(0xFF4CAF50),
                    modifier     = Modifier.size(200.dp)
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text       = "${"%.0f".format(uiState.porcentajeUsado)}%",
                        fontWeight = FontWeight.Bold,
                        fontSize   = 36.sp,
                        color      = Color(0xFF357ABD)
                    )
                    Text(
                        text     = "Usado",
                        fontSize = 16.sp,
                        color    = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // ── Restante ───────────────────────────────────────────────────
            Text(
                text       = "Restante: $${"%.2f".format(uiState.restante)}",
                fontWeight = FontWeight.SemiBold,
                fontSize   = 18.sp,
                color      = Color(0xFF222222)
            )

            Spacer(modifier = Modifier.height(40.dp))

            // ── Botón Editar Presupuesto ───────────────────────────────────
            Button(
                onClick  = { /* lógica futura */ },
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF357ABD)
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text       = "Editar Presupuesto",
                    color      = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize   = 16.sp
                )
            }
        }
    }
}

/** Gráfico de dona con dos segmentos: usado (azul) y restante (verde). */
@Composable
private fun DonutChart(
    progress    : Float,           // 0f → 1f  (fracción usada)
    usedColor   : Color,
    remainColor : Color,
    modifier    : Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val strokeWidth = 28.dp.toPx()
        val diameter    = size.minDimension - strokeWidth
        val topLeft     = Offset(
            x = (size.width  - diameter) / 2f,
            y = (size.height - diameter) / 2f
        )
        val arcSize = Size(diameter, diameter)
        val style   = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        val startAngle = -90f                       // empieza arriba

        // Arco "restante" (fondo verde)
        drawArc(
            color      = remainColor,
            startAngle = startAngle,
            sweepAngle = 360f,
            useCenter  = false,
            topLeft    = topLeft,
            size       = arcSize,
            style      = style
        )
        // Arco "usado" (azul encima)
        drawArc(
            color      = usedColor,
            startAngle = startAngle,
            sweepAngle = 360f * progress,
            useCenter  = false,
            topLeft    = topLeft,
            size       = arcSize,
            style      = style
        )
    }
}
