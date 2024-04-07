package com.example.wordsfactory.di

import com.example.wordsfactory.domain.usecase.CheckUserAuthUseCase
import com.example.wordsfactory.domain.usecase.LoginUseCase
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
}
