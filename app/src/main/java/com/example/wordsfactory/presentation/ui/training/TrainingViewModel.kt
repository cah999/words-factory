package com.example.wordsfactory.presentation.ui.training

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wordsfactory.domain.usecase.GetWordsCountUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TrainingViewModel(
    private val getWordsCountUseCase: GetWordsCountUseCase,
) : ViewModel() {

    init {
        getWordsCount()
    }


    private val _trainingState = MutableStateFlow(TrainingState())
    val trainingState = _trainingState.asStateFlow()

    fun onTimerStartedChanged(timerStarted: Boolean) {
        _trainingState.update { it.copy(timerStarted = timerStarted) }
    }

    private fun onCountChanged(count: Int) {
        _trainingState.update { it.copy(count = count) }
    }

    private fun getWordsCount() {
        viewModelScope.launch {
            val result = getWordsCountUseCase.execute()
            onCountChanged(result)
        }
    }
}
