package com.example.wordsfactory.di

import com.example.wordsfactory.common.Constants
import com.example.wordsfactory.data.repository.AuthRepositoryImpl
import com.example.wordsfactory.data.repository.DictionaryRepositoryImpl
import com.example.wordsfactory.data.service.DictionaryApiService
import com.example.wordsfactory.domain.repository.AuthRepository
import com.example.wordsfactory.domain.repository.DictionaryRepository
import com.google.firebase.auth.FirebaseAuth
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    single<FirebaseAuth> {
        FirebaseAuth.getInstance()
    }

    single<AuthRepository> {
        AuthRepositoryImpl(auth = get())
    }

    single<DictionaryRepository> {
        DictionaryRepositoryImpl(dictionaryApiService = get())
    }

    single<DictionaryApiService> {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DictionaryApiService::class.java)
    }
}