package com.example.wordsfactory.domain.repository

import com.example.wordsfactory.data.model.User
import com.example.wordsfactory.data.model.UserLogin
import com.example.wordsfactory.presentation.ui.utils.UiState

interface AuthRepository {
    fun login(loginData: UserLogin, result: (UiState) -> Unit)
    fun register(loginData: UserLogin, user: User, result: (UiState) -> Unit)
    fun logout()
    fun isUserLoggedIn(): Boolean
    fun updateProfile(user: User)
    fun getCurrentUser(): User?
}