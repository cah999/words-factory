package com.example.wordsfactory.presentation.ui.utils

sealed interface UiState {
    data object Loading : UiState
    data class Error(val message: String) : UiState
    data object Success : UiState
}