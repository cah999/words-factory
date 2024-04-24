package com.example.wordsfactory.domain.repository

import com.example.wordsfactory.presentation.ui.question.Question

interface WordRepository {
    suspend fun getWords()
    suspend fun getQuestions(count: Int): List<Question>
    suspend fun getWordsCount(): Int
    suspend fun getWord(name: String): Int
    suspend fun getWordMeanings(name: String)
    suspend fun addWord(name: String, meanings: List<String>)
    suspend fun updateWord(name: String, count: String)
    suspend fun deleteWord(name: String)
    suspend fun increaseWordCounter(word: String)
    suspend fun decreaseWordCounter(word: String)
    suspend fun getMyRememberedWords(): Int
}