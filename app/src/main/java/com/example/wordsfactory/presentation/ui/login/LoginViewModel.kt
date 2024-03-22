package com.example.wordsfactory.presentation.ui.login

import androidx.lifecycle.ViewModel
import com.example.wordsfactory.common.Constants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

class LoginViewModel : ViewModel() {
    val emailText = MutableStateFlow(Constants.EMPTY)
    val passwordText = MutableStateFlow(Constants.EMPTY)
    val passwordVisible = MutableStateFlow(false)
    val isButtonEnabled = combine(emailText, passwordText) { email, password ->
        email.isNotBlank() && password.isNotBlank()
    }
}
