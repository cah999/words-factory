package com.example.wordsfactory.data.repository

import com.example.wordsfactory.data.database.MeaningDao
import com.example.wordsfactory.data.database.WordDao
import com.example.wordsfactory.data.model.Meaning
import com.example.wordsfactory.data.model.WordTable
import com.example.wordsfactory.domain.repository.WordRepository
import com.example.wordsfactory.domain.model.Answer
import com.example.wordsfactory.domain.model.Question

class WordRepositoryImpl(
    private val wordDao: WordDao, private val meaningDao: MeaningDao
) : WordRepository {
    override suspend fun getWords() {
        wordDao.getAll()
    }

    override suspend fun getQuestions(count: Int): List<Question> {
        var allWords = wordDao.getAll()
        return wordDao.getWorseWords(count).map { word ->
            val meanings = meaningDao.getMeanings(word.id)
            if (allWords.size < 3) {
                val additionalWords = listOf(
                    WordTable(-1, "cooking", 0),
                    WordTable(-2, "smiling", 0),
                    WordTable(-3, "freezing", 0)
                )
                allWords = allWords + additionalWords.filter { additionalWord ->
                    allWords.none { it.name == additionalWord.name }
                }
            }
            val incorrectWords = allWords.filter { it.id != word.id }.shuffled().take(2)
            val answers = incorrectWords.map { Answer(it.name, false) } + Answer(word.name, true)
            Question(
                meanings?.random()?.meaning ?: "", answers.shuffled()
            )
        }.shuffled()
    }

    override suspend fun getWordsCount(): Int {
        return wordDao.getWordsCount()
    }

    override suspend fun getWord(name: String): Int? {
        return wordDao.getWordId(name)
    }

    override suspend fun getWordMeanings(name: String) {
        val wordId = wordDao.getWordId(name)
        meaningDao.getMeanings(wordId)
    }

    override suspend fun addWord(name: String, meanings: List<String>) {
        wordDao.insertAll(WordTable(name = name, count = 0))
        val wordId = wordDao.getWordId(name) ?: return
        meanings.forEach { meaningDao.insertAll(Meaning(wordId = wordId, meaning = it)) }
    }

    override suspend fun updateWord(name: String, count: String) {
        wordDao.updateWord(name, count)
    }

    override suspend fun deleteWord(name: String) {
        wordDao.deleteWord(name)
    }

    override suspend fun increaseWordCounter(word: String) {
        wordDao.increaseWordCounter(word)
    }

    override suspend fun decreaseWordCounter(word: String) {
        wordDao.decreaseWordCounter(word)
    }

    override suspend fun getMyRememberedWords(): Int {
        return wordDao.getMyRememberedWords()
    }
}