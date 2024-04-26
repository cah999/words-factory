package com.example.wordsfactory.presentation.ui.question

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.wordsfactory.common.Constants
import com.example.wordsfactory.presentation.ui.question.elements.AnswerBox
import com.example.wordsfactory.presentation.ui.question.elements.GradientProgressIndicator
import com.example.wordsfactory.ui.theme.DarkGrey
import com.example.wordsfactory.ui.theme.Error
import com.example.wordsfactory.ui.theme.Primary
import com.example.wordsfactory.ui.theme.Success
import com.example.wordsfactory.ui.theme.Yellow
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun QuestionScreen(onNavigate: (Int, Int) -> Unit, viewModel: QuestionViewModel = koinViewModel()) {
    val questionState by viewModel.dictionaryState.collectAsStateWithLifecycle()
    LaunchedEffect(questionState.currentQuestion) {
        animate(initialValue = 0f, targetValue = 1f, animationSpec = tween(
            durationMillis = Constants.QUESTION_TIME * 1000, easing = LinearEasing
        ), block = { value, _ ->
            if (questionState.answerClicked) return@animate
            viewModel.onTimerProgressChanged(value)
        })
        if (questionState.answerClicked) return@LaunchedEffect
        if (viewModel.onNextQuestion() == false) {
            onNavigate(questionState.correctQuestions, questionState.totalQuestions)
        }
    }

    LaunchedEffect(questionState.currentQuestion) {
        viewModel.onIsBlockingChanged(true)
        delay(300)
        viewModel.onAnswerClickedChanged(false)
        viewModel.onIsBlockingChanged(false)
    }

    Box(Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState())
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
                AnswerBox(modifier = Modifier.padding(8.dp),
                    answer = answer,
                    answerVariant = viewModel.getAnswerVariant(index),
                    buttonClicked = questionState.answerClicked,
                    afterClick = {
                        if (viewModel.onNextQuestion() == false) {
                            Log.d(
                                "QuestionScreen",
                                "navigating with ${questionState.correctQuestions} of ${questionState.totalQuestions}"
                            )
                            onNavigate(questionState.correctQuestions, questionState.totalQuestions)
                        }
                    },
                    onClick = {
                        if (questionState.answerClicked) return@AnswerBox
                        viewModel.onAnswerClickedChanged(true)
                        viewModel.onChosenAnswerChanged(answer)
                        viewModel.checkAnswerCorrect()
                        Log.d("QuestionScreen", "clicked answer: $answer")
                    })
            }
            Spacer(modifier = Modifier.weight(1f))
            GradientProgressIndicator(
                progress = questionState.timerProgress, colors = listOf(
                    Yellow, Primary, Error
                ), strokeWidth = 4.dp, modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp)
            )
        }
        if (questionState.currentQuestionCounter > 1) {
            AnimatedVisibility(
                visible = questionState.isBlocking, enter = fadeIn(), exit = fadeOut()
            ) {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .clickable { }
                    .background(
                        if (questionState.chosenAnswer?.isCorrect == true && questionState.answerClicked) Success.copy(
                            alpha = 0.2f
                        )
                        else Error.copy(alpha = 0.2f)
                    ))
            }
        }
    }
}


@Preview
@Composable
private fun QuestionPreview() {
    QuestionScreen(onNavigate = { _, _ -> })
}