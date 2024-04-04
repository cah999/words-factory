package com.example.wordsfactory.presentation.ui.signup

import androidx.lifecycle.ViewModel
import com.example.wordsfactory.common.Constants
import com.example.wordsfactory.data.model.User
import com.example.wordsfactory.domain.usecase.RegisterUseCase
import com.example.wordsfactory.presentation.ui.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine

class SignUpViewModel(private val registerUseCase: RegisterUseCase) : ViewModel() {
    private val _UiState = MutableStateFlow<UiState>(UiState.Default)
    val signUpUiState get() = _UiState.asStateFlow()

    val nameText = MutableStateFlow(Constants.EMPTY)
    val emailText = MutableStateFlow(Constants.EMPTY)
    val passwordText = MutableStateFlow(Constants.EMPTY)
    val passwordVisible = MutableStateFlow(false)
    val isButtonEnabled = combine(nameText, emailText, passwordText) { name, email, password ->
        name.isNotBlank() && email.isNotBlank() && password.isNotBlank()
    }

    fun signUp() {
        _UiState.value = UiState.Loading
        registerUseCase.execute(email = emailText.value,
            password = passwordText.value,
            user = User(name = nameText.value),
            result = { _UiState.value = it })
    }

}


