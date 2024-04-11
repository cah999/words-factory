package com.example.wordsfactory.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(indices = [androidx.room.Index(value = ["name"], unique = true)])
data class WordTable(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val count: Int
)

// todo add releationship between word and userId in the future