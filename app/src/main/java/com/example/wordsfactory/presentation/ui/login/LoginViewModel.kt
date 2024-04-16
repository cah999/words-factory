package com.example.wordsfactory.presentation.ui.login

import androidx.lifecycle.ViewModel
import com.example.wordsfactory.common.Constants
import com.example.wordsfactory.data.model.UserLogin
import com.example.wordsfactory.domain.usecase.LoginUseCase
import com.example.wordsfactory.presentation.ui.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel(private val loginUseCase: LoginUseCase) : ViewModel() {
    private val _loginLoginUiState = MutableStateFlow<UiState>(UiState.Default)
    val loginUiState get() = _loginLoginUiState.asStateFlow()

    private val _loginState = MutableStateFlow(LoginState())
    val loginState = _loginState.asStateFlow()

    fun onEmailTextChanged(email: String) {
        _loginState.update { it.copy(emailText = email) }
    }

    fun onPasswordTextChanged(password: String) {
        _loginState.update { it.copy(passwordText = password) }
    }

    fun onPasswordVisibilityChanged() {
        _loginState.update { it.copy(passwordVisible = !it.passwordVisible) }
    }

    fun login() {
        _loginLoginUiState.update { UiState.Loading }
        loginUseCase.execute(loginData = UserLogin(
            email = _loginState.value.emailText, password = _loginState.value.passwordText
        ), result = { res ->
            _loginLoginUiState.update {
                res
            }
        })
    }
}

data class LoginState(
    val emailText: String = Constants.DEFAULT_LOGIN,
    val passwordText: String = Constants.DEFAULT_PASSWORD,
    val passwordVisible: Boolean = false,
) {
    val isButtonEnabled = emailText.isNotBlank() && passwordText.isNotBlank()
}
