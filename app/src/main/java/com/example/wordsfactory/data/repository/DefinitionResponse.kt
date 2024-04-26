package com.example.wordsfactory.data.repository

data class DefinitionResponse(
    val definition: String,
    val example: String? = null,
    val synonyms: List<String>? = null,
    val antonyms: List<String>? = null
)