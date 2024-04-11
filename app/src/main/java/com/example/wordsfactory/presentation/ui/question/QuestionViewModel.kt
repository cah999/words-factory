package com.example.wordsfactory.presentation.ui.question

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wordsfactory.common.Constants
import com.example.wordsfactory.domain.usecase.GetQuestionsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuestionViewModel(
    private val getQuestionsUseCase: GetQuestionsUseCase,
) : ViewModel() {
    private val _questionState = MutableStateFlow(QuestionState())
    val dictionaryState = _questionState.asStateFlow()

    init {
        getQuestions()
    }

    private fun onQuestionsChanged(questions: List<Question>) {
        _questionState.update { it.copy(questions = questions) }
    }

    private fun onCurrentQuestionStateChanged(question: Question) {
        _questionState.update { it.copy(currentQuestion = question) }
    }

    private fun onCurrentQuestionCounterChanged(currentQuestion: Int) {
        _questionState.update { it.copy(currentQuestionCounter = currentQuestion) }
    }

    private fun onTotalQuestionsChanged(totalQuestions: Int) {
        _questionState.update { it.copy(totalQuestions = totalQuestions) }
    }

    private fun getQuestions() {
        viewModelScope.launch {
            val questions = getQuestionsUseCase.execute(count = Constants.QUESTIONS_COUNT)
            onQuestionsChanged(questions)
            onTotalQuestionsChanged(questions.size)
            onCurrentQuestionStateChanged(questions.first())

        }
    }

    fun getAnswerVariant(index: Int): String {
        return when (index) {
            0 -> "A"
            1 -> "B"
            2 -> "C"
            else -> ""
        }
    }

    // todo blur answer word in meaning !!
    // test if < 3 words
    // todo add placeholder if no words
    fun onNextQuestion(): Boolean {
        val currentQuestionCounter = _questionState.value.currentQuestionCounter
        val questions = _questionState.value.questions
        if (currentQuestionCounter < questions.size) {
            onCurrentQuestionStateChanged(questions[currentQuestionCounter])
            onCurrentQuestionCounterChanged(currentQuestionCounter + 1)
            return true
        }
        return false
    }

}

data class QuestionState(
    val questions: List<Question> = emptyList(),
    val currentQuestion: Question = Question("", emptyList()),
    val currentQuestionCounter: Int = 1,
    val totalQuestions: Int = 0,
)

data class Answer(
    val text: String,
    val isCorrect: Boolean,
)

data class Question(
    val question: String,
    val answers: List<Answer>,
)
