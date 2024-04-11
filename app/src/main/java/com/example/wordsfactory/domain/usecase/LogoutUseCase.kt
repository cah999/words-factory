package com.example.wordsfactory.domain.usecase

import com.example.wordsfactory.domain.repository.AuthRepository

class LogoutUseCase(private val authRepository: AuthRepository) {
    fun execute() {
        authRepository.logout()
    }
}
