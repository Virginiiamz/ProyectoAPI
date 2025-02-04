package com.munozcastrovirginia.proyectoapi.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.dp
import com.munozcastrovirginia.proyectoapi.data.AuthManager
import com.munozcastrovirginia.proyectoapi.model.Asignatura

@Composable
fun AddAsignaturaDialog(onAsignaturaAdded: (Asignatura) -> Unit, onDialogDismissed: () -> Unit, auth: AuthManager) {

    var codigo by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var horas by remember { mutableIntStateOf(0) }

    AlertDialog(
        title = { Text("Añadir asignatura") },
        onDismissRequest = {onDialogDismissed()},
        confirmButton = {
            Button(
                onClick = {
                    val newAsignatura = Asignatura(
                        userId = auth.getCurrentUser()?.uid,
                        codigo = codigo,
                        nombre = nombre,
                        descripcion = descripcion,
                        horas = horas
                    )
                    onAsignaturaAdded(newAsignatura)
                    codigo = ""
                    nombre = ""
                    descripcion = ""
                    horas = 0
                }
            ) {
                Text("Añadir")
            }
        },
        dismissButton = {
            Button(
                onClick = {onDialogDismissed()}
            ) {
                Text("Cancelar")
            }
        },
        text = {
            Column {
                TextField(
                    value = codigo,
                    onValueChange = { codigo = it },
                    label = { Text("Código") }
                )
                Spacer(modifier = Modifier.height(10.dp))
                    TextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        label = { Text("Nombre") }
                    )
                Spacer(modifier = Modifier.height(10.dp))
                    TextField(
                        value = descripcion,
                        onValueChange = { descripcion = it },
                        label = { Text("Descripcion") }
                    )
                Spacer(modifier = Modifier.height(10.dp))
                    TextField(
                        value = horas.toString(),
                        onValueChange = { horas = it.toInt() },
                        label = { Text("Total de horas") }
                    )
            }


        }

    )

}