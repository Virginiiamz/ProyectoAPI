package com.munozcastrovirginia.proyectoapi.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RickMortyCharactersClient {
    private const val BASE_URL = "https://rickandmortyapi.com/api/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val service: RickMortyCharactersService by lazy {
        retrofit.create(RickMortyCharactersService::class.java)
    }
}