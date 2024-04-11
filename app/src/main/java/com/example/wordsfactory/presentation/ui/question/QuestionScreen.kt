package com.example.wordsfactory.presentation.ui.question

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.wordsfactory.ui.theme.Dark
import com.example.wordsfactory.ui.theme.DarkGrey
import com.example.wordsfactory.ui.theme.Error
import com.example.wordsfactory.ui.theme.Grey
import com.example.wordsfactory.ui.theme.Primary
import com.example.wordsfactory.ui.theme.PrimaryLight
import com.example.wordsfactory.ui.theme.Yellow
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

// todo экран профиля
@Composable
fun QuestionScreen(onNavigate: () -> Unit = {}, viewModel: QuestionViewModel = koinViewModel()) {
    val questionState by viewModel.dictionaryState.collectAsStateWithLifecycle()

    Column(
        Modifier
            .fillMaxSize()
            .padding(top = 32.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${questionState.currentQuestionCounter} of ${questionState.totalQuestions}",
            style = MaterialTheme.typography.bodyMedium,
            color = DarkGrey
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = questionState.currentQuestion.question,
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        questionState.currentQuestion.answers.forEachIndexed { index, answer ->
            AnswerBox(
                modifier = Modifier.padding(8.dp),
                answer = answer,
                answerVariant = viewModel.getAnswerVariant(index),
                onClick = {
                    if (!viewModel.onNextQuestion()) {
                        onNavigate()
                    }
                }
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        GradientProgressIndicator(
            progress = 1f, colors = listOf(
                Yellow, Primary, Error
            ), strokeWidth = 4.dp, modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun AnswerBox(
    modifier: Modifier = Modifier,
    answer: Answer,
    answerVariant: String,
    onClick: () -> Unit = {}
) {
    var clicked by remember { mutableStateOf(false) }

    LaunchedEffect(clicked) {
        if (clicked) {
            delay(1000)
            onClick()
            clicked = false
        }
    }
    Box(
        contentAlignment = Alignment.CenterStart, modifier = modifier
            .fillMaxWidth()
            .animatedBorder(
                borderColors = listOf(Yellow, Primary, Error),
                backgroundColor = PrimaryLight,
                shape = RoundedCornerShape(16.dp),
                borderWidth = 3.dp,
                easing = LinearEasing,
                animationDurationInMillis = 1000,
                clicked = clicked,
            )
            .clickable(onClick = {
                clicked = true
            })
    ) {
        Row(Modifier.padding(24.dp)) {
            Text(
                text = "$answerVariant.",
                style = MaterialTheme.typography.bodyMedium,
                color = Dark
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = answer.text, style = MaterialTheme.typography.bodyMedium, color = Dark
            )
        }
    }
}


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

@Composable
fun Modifier.animatedBorder(
    borderColors: List<Color>,
    backgroundColor: Color,
    shape: Shape = RectangleShape,
    borderWidth: Dp = 1.dp,
    animationDurationInMillis: Int = 1000,
    easing: Easing = LinearEasing,
    clicked: Boolean = false,
): Modifier {
    val brush = Brush.sweepGradient(borderColors)
    val infiniteTransition = rememberInfiniteTransition(label = "animatedBorder")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 360f, animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = animationDurationInMillis, easing = easing),
            repeatMode = RepeatMode.Restart,
        ), label = "angleAnimation"
    )

    val progress by animateFloatAsState(
        targetValue = if (clicked) 1f else 0f,
        animationSpec = tween(durationMillis = animationDurationInMillis, easing = easing),
        label = ""
    )
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
            .background(color = backgroundColor, shape = shape)
    }
    return this
        .border(1.dp, Grey, shape)
        .clip(shape)

}


@Preview
@Composable
private fun QuestionPreview() {
    QuestionScreen()
}