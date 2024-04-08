package com.example.wordsfactory.presentation.ui.dictionary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wordsfactory.data.model.WordRequest
import com.example.wordsfactory.data.service.PhoneticResponse
import com.example.wordsfactory.data.service.WordResponse
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
                    it.copy(wordContent = result.map { word ->
                        val mergedPhonetics = mergePhonetics(word)
                        WordContent(word = word.word,
                            phonetics = mergedPhonetics.phonetics?.map { phonetic ->
                                Phonetic(
                                    transcription = "[${phonetic?.text}]", voice = phonetic?.audio
                                )
                            },
                            meanings = word.meanings.map { meaning ->
                                Meaning(partOfSpeech = meaning.partOfSpeech,
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

    fun onSearchTextChanged(searchText: String) {
        _dictionaryState.update { it.copy(searchText = searchText) }
        getWordContent()
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

    fun onDropDownExpanded(expanded: Boolean) {
        _dictionaryState.update { it.copy(dropDownExpanded = expanded) }
    }

    private fun mergePhonetics(wordResponse: WordResponse): WordResponse {
        // todo krutaya fishka tak to )
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

//val phoneticsVariant = remember { mutableIntStateOf(0) }
//val partOfSpeechVariant = remember { mutableIntStateOf(0) }
//val expanded = remember { mutableStateOf(false) }
data class DictionaryState(
    val searchText: String = "",
    val wordContent: List<WordContent>? = null,
    val isAudioLoading: Boolean = false,
    val phoneticsVariants: Map<Int, Int> = mapOf(),
    val partOfSpeechVariants: Map<Int, Int> = mapOf(),
    val dropDownExpanded: Boolean = false
)