package com.example.wordsfactory.presentation.widget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wordsfactory.domain.usecase.GetMyRememberedWordsUseCase
import com.example.wordsfactory.domain.usecase.GetWordsCountUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WidgetViewModel(
    private val getWordsCountUseCase: GetWordsCountUseCase,
    private val getMyRememberedWordsUseCase: GetMyRememberedWordsUseCase
) : ViewModel() {
    private val _widgetState = MutableStateFlow(MyWidgetState())
    val widgetState = _widgetState.asStateFlow()

    fun updateMyWords() {
        viewModelScope.launch {
            val myWords = getWordsCountUseCase.execute()
            _widgetState.update { it.copy(myWords = myWords) }
        }
    }

    fun updateMyWordsRemembered() {
        viewModelScope.launch {
            val myWordsRemembered = getMyRememberedWordsUseCase.execute()
            _widgetState.update { it.copy(myWordsRemembered = myWordsRemembered) }
        }
    }

}