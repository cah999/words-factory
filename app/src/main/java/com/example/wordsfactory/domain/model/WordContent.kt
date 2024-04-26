package com.example.wordsfactory.domain.model

data class WordContent(
    val word: String, val phonetics: List<Phonetic>?, val meanings: List<Meaning>
)