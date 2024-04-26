package com.example.wordsfactory.presentation.ui.login

import com.example.wordsfactory.common.Constants

data class LoginState(
    val emailText: String = Constants.DEFAULT_LOGIN,
    val passwordText: String = Constants.DEFAULT_PASSWORD,
    val passwordVisible: Boolean = false,
) {
    val isButtonEnabled = emailText.isNotBlank() && passwordText.isNotBlank()
}