package com.example.wordsfactory.di

import com.example.wordsfactory.presentation.ui.signup.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel<SignUpViewModel> {
        SignUpViewModel()
    }
}