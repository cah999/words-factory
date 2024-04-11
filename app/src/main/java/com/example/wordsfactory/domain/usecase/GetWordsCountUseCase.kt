package com.example.wordsfactory.domain.usecase

import com.example.wordsfactory.domain.repository.WordRepository

class GetWordsCountUseCase(private val wordRepository: WordRepository) {
    suspend fun execute(): Int {
        return wordRepository.getWordsCount()
    }
}
