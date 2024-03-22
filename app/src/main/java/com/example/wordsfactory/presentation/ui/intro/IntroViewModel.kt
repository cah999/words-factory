package com.example.wordsfactory.presentation.ui.intro

import androidx.lifecycle.ViewModel
import com.example.wordsfactory.common.Constants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

class IntroViewModel : ViewModel() {
    val nameText = MutableStateFlow(Constants.EMPTY)
    val emailText = MutableStateFlow(Constants.EMPTY)
    val passwordText = MutableStateFlow(Constants.EMPTY)
    val passwordVisible = MutableStateFlow(false)
    val isButtonEnabled = combine(nameText, emailText, passwordText) { name, email, password ->
        name.isNotBlank() && email.isNotBlank() && password.isNotBlank()
    }

}
