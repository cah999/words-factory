package com.example.wordsfactory.presentation.ui.training.elements

import androidx.compose.animation.core.EaseOutQuart
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.wordsfactory.R
import com.example.wordsfactory.ui.theme.Error
import com.example.wordsfactory.ui.theme.Primary
import com.example.wordsfactory.ui.theme.Secondary
import com.example.wordsfactory.ui.theme.Success
import com.example.wordsfactory.ui.theme.Warning
import kotlinx.coroutines.delay

@Composable
fun InfiniteProgressBar(modifier: Modifier = Modifier, onFinishedNavigate: () -> Unit, time: Int) {
    var targetProgress by remember { mutableFloatStateOf(0f) }
    val progress by animateFloatAsState(
        targetValue = targetProgress,
        animationSpec = repeatable(time, tween(durationMillis = 1000, easing = EaseOutQuart)),
        label = ""
    )
    var countdownNumber by remember { mutableIntStateOf(time) }

    val colorList = listOf(
        Primary, Secondary, Success, Warning, Error
    )
    var colorIndex by remember { mutableIntStateOf(0) }
    var backgroundAlpha by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(key1 = true) {
        targetProgress = 1f
        while (countdownNumber > 0) {
            delay(1000)
            colorIndex = (colorIndex + 1) % colorList.size
            backgroundAlpha = 1f
            countdownNumber--
        }
        backgroundAlpha = 0f
        delay(1000)
        onFinishedNavigate()
    }

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            progress = {
                1f
            },
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(16.dp)),
            color = colorList[if (colorIndex == 0) 0 else colorIndex - 1].copy(
                alpha = backgroundAlpha
            ),
            strokeWidth = 8.dp
        )
        CircularProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(16.dp)),
            color = colorList[colorIndex].copy(
                alpha = if (countdownNumber == 0) 0f else 1f
            ),
            strokeWidth = 8.dp,
            strokeCap = StrokeCap.Round,
        )
        Text(
            text = if (countdownNumber == 0) stringResource(id = R.string.go) else countdownNumber.toString(),
            style = MaterialTheme.typography.displayLarge,
            color = colorList[colorIndex],
        )
    }
}