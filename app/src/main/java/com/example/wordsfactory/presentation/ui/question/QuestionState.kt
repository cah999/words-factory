package com.example.wordsfactory.presentation.ui.question

import com.example.wordsfactory.domain.model.Answer
import com.example.wordsfactory.domain.model.Question

data class QuestionState(
    val questions: List<Question> = emptyList(),
    val currentQuestion: Question = Question("", emptyList()),
    val currentQuestionCounter: Int = 1,
    val correctQuestions: Int = 0,
    val totalQuestions: Int = 0,
    val timerProgress: Float = 0f,
    val answerClicked: Boolean = false,
    val chosenAnswer: Answer? = null,
    val isBlocking: Boolean = false,
)