package com.example.wordsfactory.domain.repository

import com.example.wordsfactory.data.model.WordRequest
import com.example.wordsfactory.data.repository.WordResponse
import com.example.wordsfactory.presentation.ui.utils.UiState

interface DictionaryRepository {
    suspend fun getWordContent(request: WordRequest, result: (UiState) -> Unit): List<WordResponse>?
}