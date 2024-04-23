package com.example.wordsfactory.domain.usecase

import com.example.wordsfactory.domain.repository.WordRepository

class IncreaseWordCounterUseCase(private val wordRepository: WordRepository) {

    suspend fun execute(word: String) {
        wordRepository.increaseWordCounter(word)
    }

}
