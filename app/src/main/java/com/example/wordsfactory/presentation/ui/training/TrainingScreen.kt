package com.example.wordsfactory.presentation.ui.training

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.wordsfactory.R
import com.example.wordsfactory.di.appModule
import com.example.wordsfactory.di.dataModule
import com.example.wordsfactory.di.domainModule
import com.example.wordsfactory.presentation.ui.utils.AccentButton
import com.example.wordsfactory.ui.theme.Dark
import com.example.wordsfactory.ui.theme.Error
import com.example.wordsfactory.ui.theme.Primary
import com.example.wordsfactory.ui.theme.Secondary
import com.example.wordsfactory.ui.theme.Success
import com.example.wordsfactory.ui.theme.Warning
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication

@Composable
fun TrainingScreen(onStartNavigate: () -> Unit, viewModel: TrainingViewModel = koinViewModel()) {
    val trainingState by viewModel.trainingState.collectAsStateWithLifecycle()
    val count = 25
    val text = stringResource(R.string.training_screen).format(count)
    val time = 5 // todo const

    val progress = if (trainingState.timerStarted) {
        1f - (trainingState.timerRemainingTime / time.toFloat())
    } else {
        0f
    }

    LaunchedEffect(trainingState.timerStarted) {
        if (trainingState.timerStarted) {
            while (trainingState.timerRemainingTime > 0) {
                viewModel.onTimerRemainingTimeChanged(trainingState.timerRemainingTime - 1)
                delay(100)
            }
        }
    }
    val annotatedString = buildAnnotatedString {
        withStyle(SpanStyle(color = Dark)) {
            append(text.substring(0, text.indexOf(count.toString())))
        }
        withStyle(SpanStyle(color = Primary)) {
            append(count.toString())
        }
        withStyle(SpanStyle(color = Dark)) {
            append(text.substring(text.indexOf(count.toString()) + count.toString().length))
        }
    }
    Box(
        modifier = Modifier
            .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text(
                text = annotatedString,
                style = MaterialTheme.typography.headlineLarge,
                color = Dark,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = stringResource(R.string.start_training),
                style = MaterialTheme.typography.headlineLarge,
                color = Dark
            )
        }
        if (!trainingState.timerStarted) {
            AccentButton(
                modifier = Modifier.align(Alignment.BottomCenter), onClick = {
                    viewModel.onTimerStartedChanged(true)
                }, isEnabled = true, text = stringResource(R.string.start)
            )
        } else {
            TimerElement(
                modifier = Modifier.align(Alignment.BottomCenter),
                time = time,
                onTimeEnd = { onStartNavigate() },
                progress = progress
            )
        }
    }
}

@Composable
fun TimerElement(
    modifier: Modifier = Modifier, time: Int, onTimeEnd: () -> Unit, progress: Float = 0f
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = time * 1000, easing = LinearEasing),
        label = ""
    )
    val colors = listOf(
        Primary, Secondary, Success, Warning, Error, Primary
    )

    val colorIndex = (animatedProgress * (colors.size - 1)).toInt()
    val colorFraction = animatedProgress * (colors.size - 1) - colorIndex
    val currentColor =
        lerp(colors[colorIndex], colors[(colorIndex + 1) % colors.size], colorFraction)

    LaunchedEffect(key1 = animatedProgress) {
        if (animatedProgress == 1f) {
            delay(100)
            onTimeEnd()
        }
    }
    Box(
        modifier = modifier.size(100.dp), contentAlignment = Alignment.Center
    ) {
        if (animatedProgress != 1f) {
            CircularProgressIndicator(
                progress = { animatedProgress },
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(16.dp)),
                color = currentColor,
                strokeWidth = 8.dp
            )
        }
        Text(
            text = if (animatedProgress == 1f) stringResource(R.string.go) else (time - (time * animatedProgress).toInt()).toString(),
            style = MaterialTheme.typography.displayLarge,
            color = currentColor
        )
    }
}

@Composable
@Preview
fun TrainingScreenPreview() {
    KoinApplication(application = {
        modules(appModule, dataModule, domainModule)
    }) {
        TrainingScreen({})
    }
}