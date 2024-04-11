package com.example.wordsfactory.data.repository

import com.example.wordsfactory.data.database.MeaningDao
import com.example.wordsfactory.data.database.WordDao
import com.example.wordsfactory.data.model.Meaning
import com.example.wordsfactory.data.model.WordTable
import com.example.wordsfactory.domain.repository.WordRepository

class WordRepositoryImpl(
    private val wordDao: WordDao, private val meaningDao: MeaningDao
) : WordRepository {
    override suspend fun getWords() {
        wordDao.getAll()
    }

    override suspend fun getWord(name: String): Int {
        return wordDao.getWordId(name)
    }

    override suspend fun getWordMeanings(name: String) {
        val wordId = wordDao.getWordId(name)
        meaningDao.getMeanings(wordId)
    }

    override suspend fun addWord(name: String, meanings: List<String>) {
        wordDao.insertAll(WordTable(name = name, count = 0))
        val wordId = wordDao.getWordId(name)
        meanings.forEach { meaningDao.insertAll(Meaning(wordId = wordId, meaning = it)) }
    }

    override suspend fun updateWord(name: String, count: String) {
        wordDao.updateWord(name, count)
    }

    override suspend fun deleteWord(name: String) {
        wordDao.deleteWord(name)
    }
}