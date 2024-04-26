package com.example.wordsfactory.presentation.ui.dictionary

import com.example.wordsfactory.domain.model.WordContent

data class DictionaryState(
    val searchText: String = "",
    val currentPage: Int = -1,
    val wordContent: List<WordContent>? = null,
    val isFavorite: Boolean = false,
    val isAudioLoading: Boolean = false,
    val phoneticsVariants: Map<Int, Int> = mapOf(),
    val partOfSpeechVariants: Map<Int, Int> = mapOf(),
    val dropDownExpanded: Map<Int, Boolean> = mapOf()
)