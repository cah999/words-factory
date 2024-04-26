package com.example.wordsfactory.data.repository

data class MeaningResponse(
    val partOfSpeech: String?,
    val definitions: List<DefinitionResponse>
)