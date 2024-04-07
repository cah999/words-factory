package com.example.wordsfactory.domain.usecase

import com.example.wordsfactory.data.model.WordRequest
import com.example.wordsfactory.data.service.WordResponse
import com.example.wordsfactory.domain.repository.DictionaryRepository
import com.example.wordsfactory.presentation.ui.utils.UiState

class GetWordUseCase(private val dictionaryRepository: DictionaryRepository) {
    suspend fun execute(request: WordRequest, result: (UiState) -> Unit): List<WordResponse>? {
        return dictionaryRepository.getWordContent(request, result)
    }
}
