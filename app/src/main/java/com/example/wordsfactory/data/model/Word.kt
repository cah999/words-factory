package com.example.wordsfactory.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(indices = [androidx.room.Index(value = ["name"], unique = true)])
data class WordTable(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    var count: Int
)