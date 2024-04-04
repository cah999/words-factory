package com.example.wordsfactory.presentation.ui.login

import androidx.lifecycle.ViewModel
import com.example.wordsfactory.common.Constants
import com.example.wordsfactory.domain.usecase.LoginUseCase
import com.example.wordsfactory.presentation.ui.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine

class LoginViewModel(private val loginUseCase: LoginUseCase) : ViewModel() {
    private val _loginUiState = MutableStateFlow<UiState>(UiState.Default)
    val loginUiState get() = _loginUiState.asStateFlow()
    val emailText = MutableStateFlow(Constants.EMPTY)
    val passwordText = MutableStateFlow(Constants.EMPTY)
    val passwordVisible = MutableStateFlow(false)
    val isButtonEnabled = combine(emailText, passwordText) { email, password ->
        email.isNotBlank() && password.isNotBlank()
    }

    fun login() {
        _loginUiState.value = UiState.Loading
        loginUseCase.execute(email = emailText.value,
            password = passwordText.value,
            result = { _loginUiState.value = it })
    }
}
