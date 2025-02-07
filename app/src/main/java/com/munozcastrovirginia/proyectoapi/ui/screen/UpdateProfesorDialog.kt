package com.munozcastrovirginia.proyectoapi.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.munozcastrovirginia.proyectoapi.model.Asignatura
import com.munozcastrovirginia.proyectoapi.model.Profesor

@Composable
fun UpdateProfesorDialog(
    profesor: Profesor,
    onProfesorUpdated: (Profesor) -> Unit,
    onDialogDismissed: () -> Unit
) {
    var nombre by remember { mutableStateOf(profesor.nombre) }
    var apellidos by remember { mutableStateOf(profesor.apellidos) }
    var email by remember { mutableStateOf(profesor.email) }

    AlertDialog(
        title = { Text(text = "Actualizar profesor") },
        onDismissRequest = {},
        confirmButton = {
            Button(
                onClick = {
                    val newProfesor = Profesor(
                        id = profesor.id,
                        asignaturaId = profesor.asignaturaId,
                        userId = profesor.userId,
                        nombre = nombre,
                        apellidos = apellidos,
                        email = email
                    )
                    onProfesorUpdated(newProfesor)
                    nombre = ""
                    apellidos = ""
                    email = ""
                }
            ) {
                Text(text = "Actualizar")
            }
        },
        dismissButton = {
            Button(
                onClick = { onDialogDismissed() }
            ) {
                Text(text = "Cancelar")
            }
        },
        text = {
            Column() {
                TextField(
                    value = nombre ?: "",
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                TextField(
                    value = apellidos ?: "",
                    onValueChange = { apellidos = it },
                    label = { Text("Apellidos") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                TextField(
                    value = email ?: "",
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    )
                )
            }
        }
    )
}