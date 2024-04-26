package com.example.wordsfactory.domain.model

data class Question(
    val question: String,
    val answers: List<Answer>,
)