package com.example.pocketledger.util

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PieChart(categoryTotals: Map<String, Double>) {
    val colors = listOf(
        Color(0xFFFF5722), Color(0xFF2196F3), Color(0xFF9C27B0),
        Color(0xFFFF9800), Color(0xFF4CAF50), Color(0xFFF44336),
        Color(0xFF607D8B)
    )

    val total = categoryTotals.values.sum()
    val angles = categoryTotals.values.map { (it / total * 360).toFloat() }

    Canvas(
        modifier = Modifier.size(200.dp)
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val radius = minOf(canvasWidth, canvasHeight) / 2 * 0.8f
        val center = Offset(canvasWidth / 2, canvasHeight / 2)

        var startAngle = 0f
        angles.forEachIndexed { index, angle ->
            drawArc(
                color = colors[index % colors.size],
                startAngle = startAngle,
                sweepAngle = angle,
                useCenter = true,
                topLeft = Offset(
                    center.x - radius,
                    center.y - radius
                ),
                size = Size(radius * 2, radius * 2)
            )
            startAngle += angle
        }
    }
}