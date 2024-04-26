package com.example.wordsfactory.data.repository

import com.example.wordsfactory.data.database.MeaningDao
import com.example.wordsfactory.data.database.WordDao
import com.example.wordsfactory.data.model.WordRequest
import com.example.wordsfactory.data.service.DefinitionResponse
import com.example.wordsfactory.data.service.MeaningResponse
import com.example.wordsfactory.data.service.WordResponse
import com.example.wordsfactory.domain.repository.DictionaryRepository
import com.example.wordsfactory.presentation.ui.utils.UiState

class DictionaryRepositoryLocalImpl(
    private val wordDao: WordDao, private val meaningDao: MeaningDao
) : DictionaryRepository {
    override suspend fun getWordContent(
        request: WordRequest, result: (UiState) -> Unit
    ): List<WordResponse>? {
        return try {
            val wordId = wordDao.getWordId(request.searchText.trim())
            if (wordId != null) {
                val meanings = meaningDao.getMeanings(wordId)
                if (meanings?.isNotEmpty() == true) {
                    result.invoke(UiState.Success)
                    return listOf(
                        WordResponse(
                            request.searchText, phonetics = null, meanings = listOf(
                                MeaningResponse(partOfSpeech = null,
                                    definitions = meanings.map { meaning ->
                                        DefinitionResponse(
                                            definition = meaning.meaning,
                                            example = null,
                                            synonyms = null,
                                            antonyms = null
                                        )
                                    })
                            )
                        )
                    )
                } else {
                    result.invoke(UiState.Error("Meanings not found"))
                    null
                }
            } else {
                result.invoke(UiState.Error("Bad internet connection. But you can try find words you have saved in the dictionary"))
                null
            }
        } catch (e: Exception) {
            result.invoke(UiState.Error(e.message ?: "An error occurred"))
            null
        }
    }
}
