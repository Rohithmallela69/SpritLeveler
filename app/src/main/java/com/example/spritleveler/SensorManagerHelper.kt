package com.example.spritleveler

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.runtime.mutableStateOf
import android.hardware.Sensor.TYPE_MAGNETIC_FIELD


class SensorManagerHelper(context: Context) : SensorEventListener {
    private val sensorManager: SensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    var orientation = mutableStateOf(Pair(0f, 0f)) // X and Y tilt values
    var azimuth = mutableStateOf(0f) // Compass azimuth (North direction)

    private val recentX = mutableListOf<Float>() // Track recent X-axis values
    private val recentY = mutableListOf<Float>() // Track recent Y-axis values

    var minX = mutableStateOf(0f)
    var maxX = mutableStateOf(0f)
    var minY = mutableStateOf(0f)
    var maxY = mutableStateOf(0f)

    fun startListening() {
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val magneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

        accelerometer?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
        magneticField?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    fun stopListening() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            when (it.sensor.type) {
                Sensor.TYPE_ACCELEROMETER -> {
                    val x = it.values[0]
                    val y = it.values[1]
                    orientation.value = Pair(x, y)

                    // Maintain recent 500 values
                    if (recentX.size >= 500) recentX.removeAt(0)
                    if (recentY.size >= 500) recentY.removeAt(0)
                    recentX.add(x)
                    recentY.add(y)

                    // Update min and max values
                    minX.value = recentX.minOrNull() ?: x
                    maxX.value = recentX.maxOrNull() ?: x
                    minY.value = recentY.minOrNull() ?: y
                    maxY.value = recentY.maxOrNull() ?: y
                }
                Sensor.TYPE_MAGNETIC_FIELD -> {
                    val magneticFieldValues = it.values
                    val rotationMatrix = FloatArray(9)
                    val orientationAngles = FloatArray(3)

                    if (SensorManager.getRotationMatrix(rotationMatrix, null, floatArrayOf(0f, 0f, 0f), magneticFieldValues)) {
                        SensorManager.getOrientation(rotationMatrix, orientationAngles)
                        azimuth.value = Math.toDegrees(orientationAngles[0].toDouble()).toFloat()
                    }
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}
