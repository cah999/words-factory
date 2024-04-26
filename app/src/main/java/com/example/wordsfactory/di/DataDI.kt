package com.example.wordsfactory.di

import androidx.room.Room
import com.example.wordsfactory.common.Constants
import com.example.wordsfactory.common.Constants.Companion.DATABASE_NAME
import com.example.wordsfactory.data.database.AppDatabase
import com.example.wordsfactory.data.repository.AuthRepositoryImpl
import com.example.wordsfactory.data.repository.DictionaryRepositoryImpl
import com.example.wordsfactory.data.repository.DictionaryRepositoryLocalImpl
import com.example.wordsfactory.data.repository.PreferencesRepositoryImpl
import com.example.wordsfactory.data.repository.WordRepositoryImpl
import com.example.wordsfactory.data.service.DictionaryApiService
import com.example.wordsfactory.domain.repository.AuthRepository
import com.example.wordsfactory.domain.repository.DictionaryRepository
import com.example.wordsfactory.domain.repository.PreferencesRepository
import com.example.wordsfactory.domain.repository.WordRepository
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.koin.androidContext
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
        DictionaryRepositoryImpl(
            dictionaryApiService = get(),
            dictionaryRepositoryLocalImpl = get()
        )
    }

    single<DictionaryRepositoryLocalImpl> {
        DictionaryRepositoryLocalImpl(
            wordDao = get(),
            meaningDao = get()
        )
    }

    single<DictionaryApiService> {
        Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(DictionaryApiService::class.java)
    }

    single<WordRepository> {
        WordRepositoryImpl(wordDao = get(), meaningDao = get())
    }

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, DATABASE_NAME).build()
    }
    single { get<AppDatabase>().getWordDao() }
    single { get<AppDatabase>().getMeaningDao() }

    single<PreferencesRepository> {
        PreferencesRepositoryImpl(context = androidContext())
    }
}