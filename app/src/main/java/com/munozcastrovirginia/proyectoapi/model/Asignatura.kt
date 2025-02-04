package com.munozcastrovirginia.proyectoapi.model

data class Asignatura(
    var id: String ?= null,
    val userId: String?,
    val codigo: String?,
    val nombre: String?,
    val descripcion: String?,
    val horas: Int?
)
