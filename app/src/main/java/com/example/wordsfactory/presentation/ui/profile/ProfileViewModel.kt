package com.example.wordsfactory.presentation.ui.profile

import androidx.lifecycle.ViewModel
import com.example.wordsfactory.data.model.User
import com.example.wordsfactory.domain.usecase.GetCurrentUserUseCase
import com.example.wordsfactory.domain.usecase.LogoutUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProfileViewModel(
    private val logoutUseCase: LogoutUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    private val _profileState = MutableStateFlow(ProfileState())
    val profileState = _profileState.asStateFlow()

    init {
        getCurrentUser()
    }

    fun logout() {
        logoutUseCase.execute()
    }

    private fun getCurrentUser() {
        val user = getCurrentUserUseCase.execute()
        _profileState.update { it.copy(user = user) }
    }
}


data class ProfileState(
    val user: User? = null
)