package com.example.wordsfactory.di

import com.example.wordsfactory.data.repository.AuthRepositoryImpl
import com.example.wordsfactory.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import org.koin.dsl.module

val dataModule = module {
    single<FirebaseAuth> {
        FirebaseAuth.getInstance()
    }

    single<AuthRepository> {
        AuthRepositoryImpl(auth = get())
    }
}