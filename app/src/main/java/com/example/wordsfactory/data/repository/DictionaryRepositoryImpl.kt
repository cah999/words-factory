package com.example.wordsfactory.data.repository

import com.example.wordsfactory.data.model.WordRequest
import com.example.wordsfactory.data.service.DictionaryApiService
import com.example.wordsfactory.domain.repository.DictionaryRepository
import com.example.wordsfactory.presentation.ui.utils.UiState

class DictionaryRepositoryImpl(
    private val dictionaryApiService: DictionaryApiService,
    private val dictionaryRepositoryLocalImpl: DictionaryRepositoryLocalImpl
) :
    DictionaryRepository {
    override suspend fun getWordContent(
        request: WordRequest,
        result: (UiState) -> Unit
    ): List<WordResponse>? {
        try {
            dictionaryApiService.getWordContent(request.searchText).let {
                if (it.isSuccessful) {
                    result.invoke(UiState.Success)
                    return it.body()
                } else {
                    result.invoke(UiState.Default)
                    return null
                }
            }
        } catch (e: Exception) {
            return dictionaryRepositoryLocalImpl.getWordContent(request, result)
        }
    }
}
