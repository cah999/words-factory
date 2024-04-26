package com.example.wordsfactory.data.database


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.wordsfactory.data.model.Meaning
import com.example.wordsfactory.data.model.WordTable


@Database(entities = [WordTable::class, Meaning::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getWordDao(): WordDao
    abstract fun getMeaningDao(): MeaningDao
}