package com.munozcastrovirginia.proyectoapi.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.munozcastrovirginia.proyectoapi.data.AuthManager
import com.munozcastrovirginia.proyectoapi.data.FirestoreManager
import com.munozcastrovirginia.proyectoapi.model.Asignatura

@Composable
fun ScreenDetalle(
    idAsignatura: String,
    auth: AuthManager,
    firestore: FirestoreManager,
) {
    val user = auth.getCurrentUser()
    val factory = InicioViewModelFactory(firestore)
    val inicioViewModel = viewModel(InicioViewModel::class.java, factory = factory)
    val asignatura by inicioViewModel.asignatura.collectAsState()

    LaunchedEffect(idAsignatura) {
        inicioViewModel.getAsignaturaById(idAsignatura)
    }

    Column {
        Text("Nombre de la asignatura: " + (asignatura?.nombre ?: "null"))
    }
}