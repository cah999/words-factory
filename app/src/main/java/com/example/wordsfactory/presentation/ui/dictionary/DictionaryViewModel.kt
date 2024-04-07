package com.example.wordsfactory.presentation.ui.dictionary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wordsfactory.data.model.WordRequest
import com.example.wordsfactory.domain.usecase.GetWordUseCase
import com.example.wordsfactory.presentation.ui.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DictionaryViewModel(private val getWordUseCase: GetWordUseCase) : ViewModel() {
    private val _dictionaryUiState = MutableStateFlow<UiState>(UiState.Default)
    val dictionaryUiState get() = _dictionaryUiState.asStateFlow()

    private val _dictionaryState = MutableStateFlow(DictionaryState())
    val dictionaryState = _dictionaryState.asStateFlow()
    private fun getWordContent() {
        _dictionaryUiState.value = UiState.Loading
        viewModelScope.launch {
            val result = getWordUseCase.execute(
                request = WordRequest(searchText = _dictionaryState.value.searchText),
                result = { res ->
                    _dictionaryUiState.update { res }
                },
            )
            if (result != null) {
                _dictionaryState.update {
                    it.copy(
                        wordContent = WordContent(
                            word = result[0].word,
                            transcription = result[0].phonetics?.getOrNull(0)?.text ?: "",
                            voice = result[0].phonetics?.getOrNull(0)?.audio ?: "",
                            partOfSpeech = result[0].meanings[0].partOfSpeech,
                            meanings = result[0].meanings.map { meaning ->
                                Meaning(
                                    definition = meaning.definitions[0].definition,
                                    example = meaning.definitions[0].example
                                )
                            }

                        )
                    )
                }
            } else {
                _dictionaryState.update { it.copy(wordContent = null) }
            }


        }
    }

    fun onSearchTextChanged(searchText: String) {
        _dictionaryState.update { it.copy(searchText = searchText) }
        getWordContent()
    }

    fun onAudioLoading(isLoading: Boolean) {
        _dictionaryState.update { it.copy(isAudioLoading = isLoading) }
    }
}


data class DictionaryState(
    val searchText: String = "",
    val wordContent: WordContent? = null,
    val isAudioLoading: Boolean = false
)
