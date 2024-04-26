package com.example.wordsfactory.data.service


import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApiService {
    @GET("entries/en/{word}")
    suspend fun getWordContent(@Path("word") word: String): Response<List<WordResponse>>
}


data class WordResponse(
    val word: String,
    val phonetics: List<PhoneticResponse?>?,
    val meanings: List<MeaningResponse>,
    val license: LicenseResponse? = null,
    val sourceUrls: List<String>? = null
)


data class LicenseResponse(
    val name: String,
    val url: String
)

data class PhoneticResponse(
    val text: String? = null,
    val audio: String? = null
)


data class MeaningResponse(
    val partOfSpeech: String?,
    val definitions: List<DefinitionResponse>
)


data class DefinitionResponse(
    val definition: String,
    val example: String? = null,
    val synonyms: List<String>? = null,
    val antonyms: List<String>? = null
)