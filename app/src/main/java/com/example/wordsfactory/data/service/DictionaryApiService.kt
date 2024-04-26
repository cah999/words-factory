package com.example.wordsfactory.data.service


import com.example.wordsfactory.data.repository.WordResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApiService {
    @GET("entries/en/{word}")
    suspend fun getWordContent(@Path("word") word: String): Response<List<WordResponse>>
}
