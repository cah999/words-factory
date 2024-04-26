package com.example.wordsfactory.domain.usecase

import com.example.wordsfactory.domain.repository.WordRepository

class IsWordFavoriteUseCase(private val wordRepository: WordRepository) {
    suspend fun execute(word: String): Int? {
        return wordRepository.getWord(word)
    }
}
