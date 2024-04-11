package com.example.wordsfactory.domain.repository

interface WordRepository {
    suspend fun getWords()
    suspend fun getWord(name: String): Int
    suspend fun getWordMeanings(name: String)
    suspend fun addWord(name: String, meanings: List<String>)
    suspend fun updateWord(name: String, count: String)
    suspend fun deleteWord(name: String)
}