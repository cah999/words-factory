package com.example.wordsfactory.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.wordsfactory.R
import com.example.wordsfactory.domain.repository.PreferencesRepository

class PreferencesRepositoryImpl(private val context: Context) : PreferencesRepository {
    override suspend fun updateLastTimeTraining() {
        val currentTime = System.currentTimeMillis()
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(context.getString(R.string.prefs), Context.MODE_PRIVATE)

        fun saveData(key: String, value: String) {
            val editor = sharedPreferences.edit()
            editor.putString(key, value)
            editor.apply()
        }

        saveData(context.getString(R.string.last_time_training), currentTime.toString())
    }

    override suspend fun getLastTimeTraining(): Long {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(context.getString(R.string.prefs), Context.MODE_PRIVATE)
        return sharedPreferences.getString(context.getString(R.string.last_time_training), "0")!!
            .toLong()
    }
}