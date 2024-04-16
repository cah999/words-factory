package com.example.wordsfactory.presentation.ui.splash

import androidx.lifecycle.ViewModel
import com.example.wordsfactory.domain.usecase.CheckUserAuthUseCase


class SplashViewModel(private val checkUserAuthUseCase: CheckUserAuthUseCase) : ViewModel() {

    fun isUserLoggedIn(): Boolean {
        return checkUserAuthUseCase.execute()
    }
}
