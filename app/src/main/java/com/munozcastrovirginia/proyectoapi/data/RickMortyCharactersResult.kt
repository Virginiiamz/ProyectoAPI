package com.munozcastrovirginia.proyectoapi.data

import com.munozcastrovirginia.proyectoapi.model.Characters
import com.munozcastrovirginia.proyectoapi.model.Info

data class RickMortyCharactersResult(
    val info: Info,
    val results: List<Characters>
)