package com.example.wordsfactory.presentation.ui.training

import androidx.lifecycle.ViewModel
import com.example.wordsfactory.presentation.ui.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TrainingViewModel : ViewModel() {

    private val _trainingUiState = MutableStateFlow<UiState>(UiState.Default)
    val trainingUiState get() = _trainingUiState.asStateFlow()


    private val _trainingState = MutableStateFlow(TrainingState())
    val trainingState = _trainingState.asStateFlow()

    fun onTimerRemainingTimeChanged(timerRemainingTime: Int) {
        _trainingState.value = _trainingState.value.copy(timerRemainingTime = timerRemainingTime)
    }

    fun onTimerStartedChanged(timerStarted: Boolean) {
        _trainingState.value = _trainingState.value.copy(timerStarted = timerStarted)
    }
}


data class TrainingState(
    val count: Int = 25,
    val timerRemainingTime: Int = 5,
    val timerStarted: Boolean = false,
)