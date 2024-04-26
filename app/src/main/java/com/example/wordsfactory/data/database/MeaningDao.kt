package com.example.wordsfactory.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.wordsfactory.data.model.Meaning

@Dao
interface MeaningDao {

    @Insert
    suspend fun insertAll(vararg secondTable: Meaning)

    @Query("SELECT * FROM Meaning WHERE wordId = :wordId")
    suspend fun getMeanings(wordId: Int?): List<Meaning>?
}