package com.example.wordsfactory.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.wordsfactory.data.model.WordTable


@Dao
interface WordDao {

    @Query("SELECT id FROM WordTable WHERE name = :name")
    suspend fun getWordId(name: String): Int

    @Query("SELECT * FROM WordTable")
    suspend fun getAll(): List<WordTable>

    @Insert
    suspend fun insertAll(vararg firstTable: WordTable)

    @Query("DELETE FROM WordTable WHERE name = :name")
    suspend fun deleteWord(name: String)

    @Query("UPDATE WordTable SET count = :count WHERE name = :name")
    suspend fun updateWord(name: String, count: String)
}

