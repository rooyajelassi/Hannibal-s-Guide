package com.example.hannibalsguide.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.material3.MaterialTheme

@Composable
fun TunisianPatternBackground(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    val bg = MaterialTheme.colorScheme.background
    val pattern = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
    val pattern2 = MaterialTheme.colorScheme.secondary.copy(alpha = 0.05f)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(bg)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val step = 56f
            val radius = 14f

            var y = 0f
            while (y < size.height + step) {
                var x = if (((y / step).toInt() % 2) == 0) 0f else step / 2
                while (x < size.width + step) {
                    drawCircle(
                        color = pattern,
                        radius = radius,
                        center = Offset(x, y),
                        style = Stroke(width = 1.5f)
                    )
                    drawCircle(
                        color = pattern2,
                        radius = radius * 0.55f,
                        center = Offset(x, y),
                        style = Stroke(width = 1.1f)
                    )
                    x += step
                }
                y += step
            }
        }

        content()
    }
}