package com.example.wordsfactory.domain.repository

import com.example.wordsfactory.data.model.User
import com.example.wordsfactory.presentation.ui.utils.UiState

interface AuthRepository {
    fun login(email: String, password: String, result: (UiState) -> Unit)
    fun register(email: String, password: String, user: User, result: (UiState) -> Unit)
    fun logout()
    fun isUserLoggedIn(): Boolean
    fun updateProfile(user: User)
}