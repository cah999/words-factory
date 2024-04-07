package com.example.wordsfactory.presentation.ui.signup

import androidx.lifecycle.ViewModel
import com.example.wordsfactory.data.model.User
import com.example.wordsfactory.data.model.UserLogin
import com.example.wordsfactory.domain.usecase.RegisterUseCase
import com.example.wordsfactory.presentation.ui.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignUpViewModel(private val registerUseCase: RegisterUseCase) : ViewModel() {
    private val _signUpUiState = MutableStateFlow<UiState>(UiState.Default)
    val signUpUiState get() = _signUpUiState.asStateFlow()

    private val _signUpState = MutableStateFlow(SignUpState())
    val signUpState = _signUpState.asStateFlow()

    fun signUp() {
        _signUpUiState.value = UiState.Loading
        registerUseCase.execute(loginData = UserLogin(
            email = _signUpState.value.emailText, password = _signUpState.value.passwordText
        ),
            user = User(name = _signUpState.value.nameText),
            result = { res -> _signUpUiState.update { res } })
    }

    fun onNameTextChanged(name: String) {
        _signUpState.update { it.copy(nameText = name) }
    }

    fun onEmailTextChanged(email: String) {
        _signUpState.update { it.copy(emailText = email) }
    }

    fun onPasswordTextChanged(password: String) {
        _signUpState.update { it.copy(passwordText = password) }
    }

    fun onPasswordVisibilityChanged() {
        _signUpState.update { it.copy(passwordVisible = !it.passwordVisible) }
    }
}


data class SignUpState(
    val nameText: String = "",
    val emailText: String = "",
    val passwordText: String = "",
    val passwordVisible: Boolean = false,
) {
    val isButtonEnabled =
        emailText.isNotBlank() && passwordText.isNotBlank() && nameText.isNotBlank()
}
