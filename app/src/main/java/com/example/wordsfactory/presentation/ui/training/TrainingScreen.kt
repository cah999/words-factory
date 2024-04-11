package com.example.wordsfactory.presentation.ui.training

import androidx.compose.animation.core.EaseOutQuart
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.repeatable
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
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.wordsfactory.R
import com.example.wordsfactory.common.Constants
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
    val text = stringResource(R.string.training_screen).format(trainingState.count)
    val annotatedString = buildAnnotatedString {
        withStyle(SpanStyle(color = Dark)) {
            append(text.substring(0, text.indexOf(trainingState.count.toString())))
        }
        withStyle(SpanStyle(color = Primary)) {
            append(trainingState.count.toString())
        }
        withStyle(SpanStyle(color = Dark)) {
            append(text.substring(text.indexOf(trainingState.count.toString()) + trainingState.count.toString().length))
        }
    }
    Column(
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(bottom = 16.dp, start = 26.dp, end = 26.dp)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = annotatedString,
            style = MaterialTheme.typography.headlineLarge,
            color = Dark,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = stringResource(R.string.start_training),
            style = MaterialTheme.typography.headlineLarge,
            color = Dark,
        )
        Spacer(modifier = Modifier.weight(1f))
        if (!trainingState.timerStarted) {
            AccentButton(
                modifier = Modifier, onClick = {
                    viewModel.onTimerStartedChanged(true)
                }, isEnabled = true, text = stringResource(R.string.start)
            )
        } else {
            InfiniteProgressBar(
                modifier = Modifier.padding(bottom = 64.dp),
                onFinishedNavigate = onStartNavigate,
                time = Constants.TIMER_TIME
            )
        }
    }
}

// todo эти переменные должны быть в viewModel?
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

@Composable
@Preview
fun TrainingScreenPreview() {
    KoinApplication(application = {
        modules(appModule, dataModule, domainModule)
    }) {
        TrainingScreen({})
    }
}