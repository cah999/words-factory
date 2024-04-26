package com.example.wordsfactory.domain.usecase

import com.example.wordsfactory.domain.repository.PreferencesRepository

class UpdateLastTimeTrainingUseCase(private val repository: PreferencesRepository) {
    suspend fun execute() {
        repository.updateLastTimeTraining()
    }
}
