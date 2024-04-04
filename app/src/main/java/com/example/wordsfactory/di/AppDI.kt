package com.example.wordsfactory.di

import com.example.wordsfactory.presentation.ui.intro.IntroViewModel
import com.example.wordsfactory.presentation.ui.login.LoginViewModel
import com.example.wordsfactory.presentation.ui.signup.SignUpViewModel
import com.example.wordsfactory.presentation.ui.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel<SignUpViewModel> {
        SignUpViewModel(registerUseCase = get())
    }

    viewModel<LoginViewModel> {
        LoginViewModel(loginUseCase = get())
    }

    viewModel<IntroViewModel> {
        IntroViewModel()
    }

    viewModel<SplashViewModel> {
        SplashViewModel()
    }
}