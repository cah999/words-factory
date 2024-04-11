package com.example.wordsfactory.domain.usecase

import com.example.wordsfactory.domain.repository.WordRepository

class AddWordToDictionaryUseCase(private val wordRepository: WordRepository) {
    suspend fun execute(name: String, meanings: List<String>) {
        wordRepository.addWord(name, meanings)
    }
}
