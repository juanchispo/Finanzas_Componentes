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
import com.juanchispo.finanzas_componentes.language.LocalStrings
import com.juanchispo.finanzas_componentes.viewmodels.BudgetViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetScreen(onBack: () -> Unit = {}, viewModel: BudgetViewModel = viewModel()) {
    val strings = LocalStrings.current
    val uiState by viewModel.uiState.collectAsState()
    val animatedProgress by animateFloatAsState(
        targetValue   = uiState.porcentajeUsado / 100f,
        animationSpec = tween(durationMillis = 900),
        label         = "donut_progress"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(strings.budgetTitle, fontWeight = FontWeight.Bold, fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = Color.White)
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
            modifier            = Modifier.padding(padding).fillMaxSize().background(Color(0xFFF5F5F5)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Text(strings.monthlyBudget, fontWeight = FontWeight.Bold, fontSize = 22.sp, color = Color(0xFF222222))

            Spacer(modifier = Modifier.height(32.dp))

            Box(contentAlignment = Alignment.Center, modifier = Modifier.size(200.dp)) {
                DonutChart(animatedProgress, usedColor = Color(0xFF357ABD), remainColor = Color(0xFF4CAF50), modifier = Modifier.size(200.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("${"%.0f".format(uiState.porcentajeUsado)}%", fontWeight = FontWeight.Bold, fontSize = 36.sp, color = Color(0xFF357ABD))
                    Text(strings.used, fontSize = 16.sp, color = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            Text("${strings.remaining} $${"%.2f".format(uiState.restante)}", fontWeight = FontWeight.SemiBold, fontSize = 18.sp, color = Color(0xFF222222))

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick  = {},
                modifier = Modifier.fillMaxWidth(0.75f).height(50.dp),
                colors   = ButtonDefaults.buttonColors(containerColor = Color(0xFF357ABD)),
                shape    = RoundedCornerShape(10.dp)
            ) {
                Text(strings.editBudget, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }
    }
}

@Composable
private fun DonutChart(progress: Float, usedColor: Color, remainColor: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val strokeWidth = 28.dp.toPx()
        val diameter    = size.minDimension - strokeWidth
        val topLeft     = Offset((size.width - diameter) / 2f, (size.height - diameter) / 2f)
        val arcSize     = Size(diameter, diameter)
        val style       = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        drawArc(color = remainColor, startAngle = -90f, sweepAngle = 360f, useCenter = false, topLeft = topLeft, size = arcSize, style = style)
        drawArc(color = usedColor,   startAngle = -90f, sweepAngle = 360f * progress, useCenter = false, topLeft = topLeft, size = arcSize, style = style)
    }
}
