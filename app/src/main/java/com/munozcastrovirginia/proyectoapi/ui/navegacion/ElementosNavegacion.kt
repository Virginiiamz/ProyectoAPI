package com.munozcastrovirginia.proyectoapi.ui.navegacion

import kotlinx.serialization.Serializable
import java.io.Serial

@Serializable
object login

@Serializable
object signUp

@Serializable
object forgotPassword

@Serializable
object screenInicio

@Serializable
data class screenDetalle(val id: String)

