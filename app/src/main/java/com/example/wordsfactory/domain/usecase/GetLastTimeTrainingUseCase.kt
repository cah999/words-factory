package com.example.wordsfactory.domain.usecase

import com.example.wordsfactory.domain.repository.PreferencesRepository

class GetLastTimeTrainingUseCase(private val repository: PreferencesRepository) {
    suspend fun execute(): Long {
        return repository.getLastTimeTraining()
    }
}
