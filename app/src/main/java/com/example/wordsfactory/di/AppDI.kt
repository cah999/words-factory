package com.example.wordsfactory.di

import com.example.wordsfactory.presentation.ui.dictionary.DictionaryViewModel
import com.example.wordsfactory.presentation.ui.intro.IntroViewModel
import com.example.wordsfactory.presentation.ui.login.LoginViewModel
import com.example.wordsfactory.presentation.ui.profile.ProfileViewModel
import com.example.wordsfactory.presentation.ui.question.QuestionViewModel
import com.example.wordsfactory.presentation.ui.signup.SignUpViewModel
import com.example.wordsfactory.presentation.ui.splash.SplashViewModel
import com.example.wordsfactory.presentation.ui.training.TrainingViewModel
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
        SplashViewModel(checkUserAuthUseCase = get())
    }

    viewModel<DictionaryViewModel> {
        DictionaryViewModel(
            getWordUseCase = get(),
            addWordToDictionaryUseCase = get(),
            isWordFavoriteUseCase = get(),
            deleteFavoriteUseCase = get()
        )
    }

    viewModel<TrainingViewModel> {
        TrainingViewModel(getWordsCountUseCase = get())
    }

    viewModel<QuestionViewModel> {
        QuestionViewModel(getQuestionsUseCase = get())
    }

    viewModel<ProfileViewModel> {
        ProfileViewModel(
            logoutUseCase = get(),
            getCurrentUserUseCase = get()
        )
    }
}