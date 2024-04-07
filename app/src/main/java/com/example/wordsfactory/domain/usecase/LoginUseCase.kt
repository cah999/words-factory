package com.example.wordsfactory.domain.usecase

import com.example.wordsfactory.data.model.UserLogin
import com.example.wordsfactory.domain.repository.AuthRepository
import com.example.wordsfactory.presentation.ui.utils.UiState

class LoginUseCase(
    private val authRepository: AuthRepository
) {
    fun execute(
        loginData: UserLogin, result: (UiState) -> Unit
    ) {
        return authRepository.login(loginData, result)
    }
}