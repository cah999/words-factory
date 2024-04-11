package com.example.wordsfactory.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = WordTable::class,
        parentColumns = ["id"],
        childColumns = ["wordId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Meaning(
    @PrimaryKey(autoGenerate = true) val meaningId: Int = 0,
    val wordId: Int,
    val meaning: String
)