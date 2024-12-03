package com.example.spritleveler

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun BubbleLevel1D(xTilt: Float) {
    val range = 10f // Tilt range [-10, 10]
    val bubblePosition = (xTilt / range).coerceIn(-1f, 1f)

    Canvas(
        modifier = Modifier
            .width(400.dp)
            .height(100.dp)
    ) {
        // Calculate bubble position in pixels relative to canvas size
        val centerX = size.width / 2
        val bubbleX = centerX + bubblePosition * centerX

        // Draw the background bar
        drawLine(
            color = Color.Gray,
            start = Offset(0f, size.height / 2),
            end = Offset(size.width, size.height / 2),
            strokeWidth = 10f
        )

        // Draw the bubble
        drawCircle(
            color = Color.Blue,
            radius = 20f,
            center = Offset(bubbleX, size.height / 2)
        )
    }
}