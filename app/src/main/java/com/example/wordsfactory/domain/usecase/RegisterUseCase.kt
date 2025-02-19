package com.example.wordsfactory.domain.usecase

import com.example.wordsfactory.data.model.User
import com.example.wordsfactory.data.model.UserLogin
import com.example.wordsfactory.domain.repository.AuthRepository
import com.example.wordsfactory.presentation.ui.utils.UiState

class RegisterUseCase(
    private val authRepository: AuthRepository
) {
    fun execute(
        loginData: UserLogin, user: User, result: (UiState) -> Unit
    ) {
        return authRepository.register(loginData, user, result)
    }
}