package com.munozcastrovirginia.proyectoapi.ui.screen

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun DeleteProfesorDialog(onConfirmDelete: () -> Unit, onDismiss: () -> Unit){
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Borrar profesor") },
        text = { Text("¿Estas seguro de borrar el profesor?") },
        confirmButton = {
            Button(
                onClick = onConfirmDelete
            ) {
                Text("Aceptar")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss
            ) {
                Text("Cancelar")
            }
        }
    )
}