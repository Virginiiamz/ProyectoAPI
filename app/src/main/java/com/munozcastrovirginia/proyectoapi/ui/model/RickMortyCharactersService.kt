package com.munozcastrovirginia.proyectoapi.model

import com.munozcastrovirginia.proyectoapi.model.RickMortyCharactersResult
import retrofit2.http.GET
import retrofit2.http.Query

interface RickMortyCharactersService {
    @GET("character/")
    suspend fun listCharacters(@Query("page") page: Int): RickMortyCharactersResult

}
