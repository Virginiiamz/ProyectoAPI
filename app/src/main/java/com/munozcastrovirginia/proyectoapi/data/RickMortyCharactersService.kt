package com.munozcastrovirginia.proyectoapi.data

import retrofit2.http.GET
import retrofit2.http.Query

interface RickMortyCharactersService {
    @GET("character/")
    suspend fun listCharacters(@Query("page") page: Int): RickMortyCharactersResult

}
