package com.example.wordsfactory.presentation.ui.question

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wordsfactory.common.Constants
import com.example.wordsfactory.domain.model.Answer
import com.example.wordsfactory.domain.model.Question
import com.example.wordsfactory.domain.usecase.DecreaseWordCounterUseCase
import com.example.wordsfactory.domain.usecase.GetQuestionsUseCase
import com.example.wordsfactory.domain.usecase.IncreaseWordCounterUseCase
import com.example.wordsfactory.domain.usecase.UpdateLastTimeTrainingUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuestionViewModel(
    private val getQuestionsUseCase: GetQuestionsUseCase,
    private val increaseWordCounterUseCase: IncreaseWordCounterUseCase,
    private val decreaseWordCounterUseCase: DecreaseWordCounterUseCase,
    private val updateLastTimeTrainingUseCase: UpdateLastTimeTrainingUseCase
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

    fun onTimerProgressChanged(progress: Float) {
        _questionState.update { it.copy(timerProgress = progress) }
    }

    fun onAnswerClickedChanged(answerClicked: Boolean) {
        _questionState.update { it.copy(answerClicked = answerClicked) }
    }

    fun onChosenAnswerChanged(answer: Answer) {
        _questionState.update { it.copy(chosenAnswer = answer) }
    }

    private fun onCorrectQuestionsChanged(correctQuestions: Int) {
        _questionState.update { it.copy(correctQuestions = correctQuestions) }
    }

    fun getQuestions() {
        viewModelScope.launch {
            val questions = getQuestionsUseCase.execute(count = Constants.QUESTIONS_COUNT)
            onQuestionsChanged(questions)
            onTotalQuestionsChanged(questions.size)
            onCurrentQuestionStateChanged(questions.first())
            onTimerProgressChanged(1f)
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

    private suspend fun increaseWordCounter() {
        increaseWordCounterUseCase.execute(_questionState.value.chosenAnswer?.text ?: "")
    }

    private suspend fun decreaseWordCounter() {
        decreaseWordCounterUseCase.execute(_questionState.value.currentQuestion.answers.first { it.isCorrect }.text)
    }

    fun onIsBlockingChanged(isBlocking: Boolean) {
        _questionState.update { it.copy(isBlocking = isBlocking) }
    }

    fun onNextQuestion(): Boolean? {
        if (_questionState.value.chosenAnswer !in _questionState.value.currentQuestion.answers) {
            return null
        }
        onTimerProgressChanged(0f)
        val currentQuestionCounter = _questionState.value.currentQuestionCounter
        val questions = _questionState.value.questions
        if (currentQuestionCounter < questions.size) {
            onCurrentQuestionStateChanged(questions[currentQuestionCounter])
            onCurrentQuestionCounterChanged(currentQuestionCounter + 1)
            onTimerProgressChanged(1f)
            return true
        }
        updateLastTimeTraining()
        return false
    }

    fun checkAnswerCorrect() {
        if (_questionState.value.answerClicked) {
            if (_questionState.value.chosenAnswer !in _questionState.value.currentQuestion.answers) {
                return
            }
            if (_questionState.value.chosenAnswer?.isCorrect == true) {
                onCorrectQuestionsChanged(_questionState.value.correctQuestions + 1)
                viewModelScope.launch {
                    increaseWordCounter()
                }
            } else {
                viewModelScope.launch {
                    decreaseWordCounter()
                }
            }
        }
    }

    private fun updateLastTimeTraining() {
        viewModelScope.launch {
            updateLastTimeTrainingUseCase.execute()
        }
    }

}
