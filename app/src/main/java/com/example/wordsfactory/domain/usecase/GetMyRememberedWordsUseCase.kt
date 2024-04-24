package com.example.wordsfactory.domain.usecase

import com.example.wordsfactory.domain.repository.WordRepository

class GetMyRememberedWordsUseCase(
    private val wordRepository: WordRepository
) {
    suspend fun execute(): Int = wordRepository.getMyRememberedWords()
}
