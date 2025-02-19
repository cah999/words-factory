package com.example.wordsfactory.presentation.ui.dictionary

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wordsfactory.common.Constants.Companion.DEBOUNCE_TIMEOUT
import com.example.wordsfactory.data.model.WordRequest
import com.example.wordsfactory.data.repository.PhoneticResponse
import com.example.wordsfactory.data.repository.WordResponse
import com.example.wordsfactory.domain.model.Definition
import com.example.wordsfactory.domain.model.Meaning
import com.example.wordsfactory.domain.model.Phonetic
import com.example.wordsfactory.domain.model.WordContent
import com.example.wordsfactory.domain.usecase.AddWordToDictionaryUseCase
import com.example.wordsfactory.domain.usecase.DeleteFavoriteUseCase
import com.example.wordsfactory.domain.usecase.GetWordUseCase
import com.example.wordsfactory.domain.usecase.IsWordFavoriteUseCase
import com.example.wordsfactory.presentation.ui.utils.UiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class DictionaryViewModel(
    private val getWordUseCase: GetWordUseCase,
    private val addWordToDictionaryUseCase: AddWordToDictionaryUseCase,
    private val isWordFavoriteUseCase: IsWordFavoriteUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase
) : ViewModel() {
    private val _dictionaryUiState = MutableStateFlow<UiState>(UiState.Default)
    val dictionaryUiState get() = _dictionaryUiState.asStateFlow()


    private val _dictionaryState = MutableStateFlow(DictionaryState())
    val dictionaryState = _dictionaryState.asStateFlow()
    private var debounceJob: Job? = null

    private fun debouncedFunction() {
        debounceJob?.cancel()
        debounceJob = viewModelScope.launch {
            delay(DEBOUNCE_TIMEOUT)
            getWordContent()
            isWordFavorite(_dictionaryState.value.searchText)
        }
    }


    private fun getWordContent() {
        _dictionaryUiState.value = UiState.Loading
        onPhoneticsVariantReset()
        onPartOfSpeechVariantReset()
        if (_dictionaryState.value.searchText.isEmpty()) {
            _dictionaryUiState.value = UiState.Default
            return
        }
        viewModelScope.launch {
            val result = getWordUseCase.execute(
                request = WordRequest(searchText = _dictionaryState.value.searchText),
                result = { res ->
                    _dictionaryUiState.update { res }
                },
            )
            if (result != null) {
                _dictionaryState.update {
                    it.copy(wordContent = result.map { word ->
                        val mergedPhonetics = mergePhonetics(word)
                        WordContent(word = word.word,
                            phonetics = mergedPhonetics.phonetics?.map { phonetic ->
                                Phonetic(
                                    transcription = if (phonetic?.text != null) {
                                        "[${phonetic.text}]"
                                    } else {
                                        null
                                    }, voice = phonetic?.audio
                                )
                            },
                            meanings = word.meanings.map { meaning ->
                                Meaning(partOfSpeech = meaning.partOfSpeech ?: "",
                                    definitions = meaning.definitions.map { definition ->
                                        Definition(
                                            definition = definition.definition,
                                            example = definition.example
                                        )
                                    })
                            })
                    })
                }
            } else {
                _dictionaryState.update { it.copy(wordContent = null) }
            }


        }
    }

    private fun isWordFavorite(word: String) {
        viewModelScope.launch {
            val isFavorite = isWordFavoriteUseCase.execute(word)
            Log.d("isFavorite", isFavorite.toString())
            if (isFavorite != 0 && isFavorite != null) {
                _dictionaryState.update { it.copy(isFavorite = true) }
            } else {
                _dictionaryState.update { it.copy(isFavorite = false) }
            }
        }
    }

    fun removeFromDictionary() {
        viewModelScope.launch {
            val wordContent = _dictionaryState.value.wordContent ?: return@launch
            val wordName = wordContent.first().word
            deleteFavoriteUseCase.execute(wordName)
            _dictionaryState.update { it.copy(isFavorite = false) }
        }
    }

    fun addToDictionary() {
        viewModelScope.launch {
            val wordContent = _dictionaryState.value.wordContent ?: return@launch
            val wordName = wordContent.first().word
            val meanings = wordContent.map { word ->
                word.meanings.map { meaning ->
                    meaning.definitions.map { definition ->
                        definition.definition
                    }
                }.flatten()
            }.flatten()
            addWordToDictionaryUseCase.execute(
                name = wordName, meanings = meanings
            )
            _dictionaryState.update { it.copy(isFavorite = true) }
        }
    }

    fun onSearchTextChanged(searchText: String) {
        _dictionaryState.update { it.copy(searchText = searchText) }
        debouncedFunction()
    }

    fun onAudioLoading(isLoading: Boolean) {
        _dictionaryState.update { it.copy(isAudioLoading = isLoading) }
    }

    fun onPhoneticsVariantChanged(page: Int, variant: Int) {
        _dictionaryState.update {
            it.copy(phoneticsVariants = it.phoneticsVariants + (page to variant))
        }
    }

    fun onPartOfSpeechVariantChanged(page: Int, variant: Int) {
        _dictionaryState.update {
            it.copy(partOfSpeechVariants = it.partOfSpeechVariants + (page to variant))
        }
    }

    private fun onPartOfSpeechVariantReset() {
        _dictionaryState.update {
            it.copy(partOfSpeechVariants = mapOf())
        }
    }

    private fun onPhoneticsVariantReset() {
        _dictionaryState.update {
            it.copy(phoneticsVariants = mapOf())
        }
    }

    fun onDropDownExpanded(page: Int, expanded: Boolean) {
        _dictionaryState.update { it.copy(dropDownExpanded = it.dropDownExpanded + (page to expanded)) }
    }

    fun onCurrentPageChanged(page: Int) {
        _dictionaryState.update { it.copy(currentPage = page) }
    }

    fun getTranscriptionType(phonetic: Phonetic): String {
        if ("us" in phonetic.voice.orEmpty()) {
            return "US"
        }
        if ("uk" in phonetic.voice.orEmpty()) {
            return "UK"
        }
        if ("au" in phonetic.voice.orEmpty()) {
            return "AU"
        }
        return "[?]"
    }

    fun onDismissError() {
        _dictionaryUiState.update { UiState.Default }
    }

    private fun mergePhonetics(wordResponse: WordResponse): WordResponse {
        // https://api.dictionaryapi.dev/api/v2/entries/en/serve
        val phonetics = wordResponse.phonetics ?: return wordResponse
        val mergedPhonetics = phonetics.fold(mutableListOf<PhoneticResponse>()) { acc, phonetic ->
            if (phonetic != null) {
                if (acc.isNotEmpty() && acc.last().audio?.isNotEmpty() == true && acc.last().text.isNullOrEmpty() && phonetic.text != null) {
                    val last = acc.last().copy(text = phonetic.text)
                    acc[acc.size - 1] = last
                } else {
                    acc.add(phonetic)
                }
            }
            acc
        }
        return wordResponse.copy(phonetics = mergedPhonetics)
    }
}

