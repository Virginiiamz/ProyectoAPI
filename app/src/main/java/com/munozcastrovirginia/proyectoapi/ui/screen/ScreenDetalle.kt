package com.munozcastrovirginia.proyectoapi.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.munozcastrovirginia.proyectoapi.data.AuthManager
import com.munozcastrovirginia.proyectoapi.data.FirestoreManager
import com.munozcastrovirginia.proyectoapi.model.Asignatura

@Composable
fun ScreenDetalle(
    idAsignatura: String
) {
    Column {
        Text("ID de la asignatura: " + idAsignatura)
    }
}