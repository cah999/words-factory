package com.example.wordsfactory.presentation.ui.question.elements

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun GradientProgressIndicator(
    modifier: Modifier = Modifier,
    progress: Float,
    colors: List<Color>,
    strokeWidth: Dp = 2.dp,
) {
    val gradient = Brush.horizontalGradient(colors)
    Canvas(modifier = modifier.height(strokeWidth)) {
        drawLine(
            brush = gradient,
            start = Offset.Zero,
            end = Offset(size.width * progress, 0f),
            strokeWidth = strokeWidth.toPx(),
            cap = StrokeCap.Round
        )
    }
}