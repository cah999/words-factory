package com.example.wordsfactory.domain.usecase

import com.example.wordsfactory.domain.repository.AuthRepository
import com.example.wordsfactory.presentation.ui.utils.UiState

class LoginUseCase(
    private val authRepository: AuthRepository
) {
    fun execute(
        email: String, password: String, result: (UiState) -> Unit
    ) {
        return authRepository.login(email, password, result)
    }
}