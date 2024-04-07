package com.example.wordsfactory.domain.usecase

import com.example.wordsfactory.domain.repository.AuthRepository

class CheckUserAuthUseCase(private val authRepository: AuthRepository) {
    fun execute(): Boolean {
        return authRepository.isUserLoggedIn()
    }
}