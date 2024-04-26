package com.example.wordsfactory.domain.usecase

import com.example.wordsfactory.domain.repository.WordRepository
import com.example.wordsfactory.domain.model.Question

class GetQuestionsUseCase(private val wordRepository: WordRepository) {
    suspend fun execute(count: Int): List<Question> = wordRepository.getQuestions(count)
}
