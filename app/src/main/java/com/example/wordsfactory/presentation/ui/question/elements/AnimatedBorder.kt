package com.example.wordsfactory.presentation.ui.question.elements

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.wordsfactory.ui.theme.Grey

@Composable
fun Modifier.animatedBorder(
    borderColors: List<Color>,
    backgroundColor: Color,
    shape: Shape,
    borderWidth: Dp = 2.dp,
    animationDurationInMillis: Int = 1000,
    easing: Easing = LinearEasing,
    clicked: Boolean = false,
    onClick: () -> Unit = {}
): Modifier {
    val brush = Brush.sweepGradient(borderColors)
    val infiniteTransition = rememberInfiniteTransition(label = "animatedBorder")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 360f, animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = animationDurationInMillis * 2, easing = easing),
            repeatMode = RepeatMode.Restart,
        ), label = "angleAnimation"
    )

    val progress by animateFloatAsState(targetValue = if (clicked) 1f else 0f,
        animationSpec = tween(
            durationMillis = animationDurationInMillis, easing = LinearEasing
        ),
        label = "",
        finishedListener = {
            if (clicked) onClick()
        })
    if (clicked) {
        return this
            .clip(shape)
            .padding(borderWidth)
            .drawWithContent {
                rotate(angle) {
                    drawCircle(
                        brush = brush,
                        radius = size.width * progress,
                        blendMode = BlendMode.SrcIn,
                    )
                }
                drawContent()
            }
            .background(
                color = backgroundColor, shape = RoundedCornerShape(14.dp)
            )  // shape - border width
    }
    return this
        .clip(shape)
        .border(1.dp, Grey, shape)
        .background(color = Color.Transparent, shape = shape)
}