package com.example.wordsfactory.domain.repository

interface PreferencesRepository {
    suspend fun updateLastTimeTraining()

    suspend fun getLastTimeTraining(): Long
}