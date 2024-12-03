package com.example.spritleveler

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {

    private lateinit var sensorHelper: SensorManagerHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize sensorHelper
        sensorHelper = SensorManagerHelper(this)
        sensorHelper.startListening()

        setContent {
            BubbleLevelApp(sensorHelper)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorHelper.stopListening()
    }
}

@Composable
fun BubbleLevelApp(sensorHelper: SensorManagerHelper) {
    val orientation = sensorHelper.orientation.value
    val xTilt = orientation.first
    val yTilt = orientation.second
    val azimuth = sensorHelper.azimuth.value

    val minX = sensorHelper.minX.value
    val maxX = sensorHelper.maxX.value
    val minY = sensorHelper.minY.value
    val maxY = sensorHelper.maxY.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF1E88E5), Color(0xFF64B5F6), Color(0xFFBBDEFB))
                )
            )
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "2D Bubble Level with North",
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            )

            // Bubble level
            BubbleLevel2D(xTilt, yTilt, azimuth)

            // Current values
            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                ValueCard(label = "X Angle", value = "%.2f°".format(xTilt))
                ValueCard(label = "Y Angle", value = "%.2f°".format(yTilt))
            }

            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                ValueCard(label = "Min X", value = "%.2f°".format(minX))
                ValueCard(label = "Max X", value = "%.2f°".format(maxX))
                ValueCard(label = "Min Y", value = "%.2f°".format(minY))
                ValueCard(label = "Max Y", value = "%.2f°".format(maxY))
            }
        }
    }
}

@Composable
fun ValueCard(label: String, value: String) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .size(60.dp, 60.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = label, style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold))
            Text(text = value, style = MaterialTheme.typography.bodySmall)
        }
    }
}

