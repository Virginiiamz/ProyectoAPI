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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.munozcastrovirginia.proyectoapi.data.AuthManager
import com.munozcastrovirginia.proyectoapi.model.Asignatura
import com.munozcastrovirginia.proyectoapi.model.Profesor

@Composable
fun AddProfesorDialog(
    onProfesorAdded: (Profesor) -> Unit,
    onDialogDismissed: () -> Unit,
    auth: AuthManager,
) {

    var asignaturaId by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    AlertDialog(
        title = { Text("Añadir profesor") },
        onDismissRequest = { onDialogDismissed() },
        confirmButton = {
            Button(
                onClick = {
                    val newProfesor = Profesor(
                        asignaturaId = asignaturaId,
                        userId = auth.getCurrentUser()?.uid,
                        nombre = nombre,
                        apellidos = apellidos,
                        email = email
                    )
                    onProfesorAdded(newProfesor)
                    asignaturaId = ""
                    nombre = ""
                    apellidos = ""
                    email = ""
                }
            ) {
                Text("Añadir")
            }
        },
        dismissButton = {
            Button(
                onClick = { onDialogDismissed() }
            ) {
                Text("Cancelar")
            }
        },
        text = {
            Column {
                TextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") }
                )
                Spacer(modifier = Modifier.height(10.dp))
                TextField(
                    value = apellidos,
                    onValueChange = { apellidos = it },
                    label = { Text("Apellidos") }
                )
                Spacer(modifier = Modifier.height(10.dp))
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") }
                )
            }


        }

    )

}