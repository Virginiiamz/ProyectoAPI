package com.munozcastrovirginia.proyectoapi.model

data class Profesor(
    val id: String ?= null,
    val asignaturaId: String?,
    val userId: String?,
    val nombre: String?,
    val apellidos: String?,
    val email: String?,
)
