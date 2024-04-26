package com.example.wordsfactory.presentation.ui.signup

data class SignUpState(
    val nameText: String = "",
    val emailText: String = "",
    val passwordText: String = "",
    val passwordVisible: Boolean = false,
) {
    val isButtonEnabled =
        emailText.isNotBlank() && passwordText.isNotBlank() && nameText.isNotBlank()
}