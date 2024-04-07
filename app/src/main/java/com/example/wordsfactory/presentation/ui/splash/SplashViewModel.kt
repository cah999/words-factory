package com.example.wordsfactory.presentation.ui.splash

import androidx.lifecycle.ViewModel
import com.example.wordsfactory.domain.usecase.CheckUserAuthUseCase

// todo Что по логауту? ??

// navigation rail or drawer
class SplashViewModel(private val checkUserAuthUseCase: CheckUserAuthUseCase) : ViewModel() {

    fun isUserLoggedIn(): Boolean {
        return checkUserAuthUseCase.execute()
    }
}
