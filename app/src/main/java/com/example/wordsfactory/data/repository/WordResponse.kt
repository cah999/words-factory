package com.example.wordsfactory.data.repository

data class WordResponse(
    val word: String,
    val phonetics: List<PhoneticResponse?>?,
    val meanings: List<MeaningResponse>,
    val license: LicenseResponse? = null,
    val sourceUrls: List<String>? = null
)