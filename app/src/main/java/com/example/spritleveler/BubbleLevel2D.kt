package com.example.spritleveler

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import kotlin.math.absoluteValue

@Composable
fun BubbleLevel2D(xTilt: Float, yTilt: Float, azimuth: Float) {
    val animatedX = animateFloatAsState(targetValue = xTilt.coerceIn(-10f, 10f))
    val animatedY = animateFloatAsState(targetValue = yTilt.coerceIn(-10f, 10f))

    Canvas(
        modifier = Modifier
            .size(200.dp)
            .padding(16.dp)
    ) {
        val range = 10f
        val centerX = size.width / 2
        val centerY = size.height / 2

        val bubbleX = centerX + (animatedX.value / range) * centerX
        val bubbleY = centerY - (animatedY.value / range) * centerY

        // Draw outer circle
        drawCircle(
            color = Color.Gray,
            radius = size.width / 2,
            style = Stroke(width = 5f)
        )

        // Draw North line
        val northX = centerX + (size.width / 2) * kotlin.math.sin(Math.toRadians(azimuth.toDouble())).toFloat()
        val northY = centerY - (size.height / 2) * kotlin.math.cos(Math.toRadians(azimuth.toDouble())).toFloat()
        drawLine(
            color = Color.Red,
            start = Offset(centerX, centerY),
            end = Offset(northX, northY),
            strokeWidth = 4f
        )

        // Draw bubble
        drawCircle(
            color = if (animatedX.value.absoluteValue > 8 || animatedY.value.absoluteValue > 8) Color.Red else Color.Blue,
            radius = 15f,
            center = Offset(bubbleX, bubbleY)
        )
    }
}