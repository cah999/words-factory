package com.example.wordsfactory.domain.usecase

import com.example.wordsfactory.data.model.User
import com.example.wordsfactory.domain.repository.AuthRepository

class GetCurrentUserUseCase(
    private val authRepository: AuthRepository
) {

    fun execute(): User? {
        return authRepository.getCurrentUser()
    }
}
