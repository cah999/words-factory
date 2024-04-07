package com.example.wordsfactory.data.repository

import android.util.Log
import com.example.wordsfactory.data.model.WordRequest
import com.example.wordsfactory.data.service.DictionaryApiService
import com.example.wordsfactory.data.service.WordResponse
import com.example.wordsfactory.domain.repository.DictionaryRepository
import com.example.wordsfactory.presentation.ui.utils.UiState

class DictionaryRepositoryImpl(private val dictionaryApiService: DictionaryApiService) :
    DictionaryRepository {
    override suspend fun getWordContent(
        request: WordRequest,
        result: (UiState) -> Unit
    ): List<WordResponse>? {
        dictionaryApiService.getWordContent(request.searchText).let {
            Log.d("DictionaryRepositoryImpl", "getWordContent: $it")
            Log.d("DictionaryRepositoryImpl", "getWordContent: ${it.body()}")
            if (it.isSuccessful) {
                result.invoke(UiState.Success)
                return it.body()
            } else {
                result.invoke(UiState.Error(it.errorBody()?.string() ?: "An error occurred"))
                return null
            }
        }
    }
}
