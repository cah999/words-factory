package com.example.wordsfactory.di

import com.example.wordsfactory.domain.usecase.AddWordToDictionaryUseCase
import com.example.wordsfactory.domain.usecase.CheckUserAuthUseCase
import com.example.wordsfactory.domain.usecase.DeleteFavoriteUseCase
import com.example.wordsfactory.domain.usecase.GetCurrentUserUseCase
import com.example.wordsfactory.domain.usecase.GetQuestionsUseCase
import com.example.wordsfactory.domain.usecase.GetWordUseCase
import com.example.wordsfactory.domain.usecase.GetWordsCountUseCase
import com.example.wordsfactory.domain.usecase.IsWordFavoriteUseCase
import com.example.wordsfactory.domain.usecase.LoginUseCase
import com.example.wordsfactory.domain.usecase.LogoutUseCase
import com.example.wordsfactory.domain.usecase.RegisterUseCase
import org.koin.dsl.module

val domainModule = module {

    factory<RegisterUseCase> {
        RegisterUseCase(authRepository = get())
    }

    factory<LoginUseCase> {
        LoginUseCase(authRepository = get())
    }

    factory<CheckUserAuthUseCase> {
        CheckUserAuthUseCase(authRepository = get())
    }

    factory<GetWordUseCase> {
        GetWordUseCase(dictionaryRepository = get())
    }

    factory<AddWordToDictionaryUseCase> {
        AddWordToDictionaryUseCase(wordRepository = get())
    }

    factory<IsWordFavoriteUseCase> {
        IsWordFavoriteUseCase(wordRepository = get())
    }

    factory<DeleteFavoriteUseCase> {
        DeleteFavoriteUseCase(wordRepository = get())
    }

    factory<GetWordsCountUseCase> {
        GetWordsCountUseCase(wordRepository = get())
    }

    factory<GetQuestionsUseCase> {
        GetQuestionsUseCase(wordRepository = get())
    }

    factory<GetCurrentUserUseCase> {
        GetCurrentUserUseCase(authRepository = get())
    }

    factory<LogoutUseCase> {
        LogoutUseCase(authRepository = get())
    }

}
