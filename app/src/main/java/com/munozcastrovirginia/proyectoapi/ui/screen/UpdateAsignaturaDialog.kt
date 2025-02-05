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
import com.munozcastrovirginia.proyectoapi.model.Asignatura

@Composable
fun UpdateAsignaturaDialog(
    asignatura: Asignatura,
    onAsignaturaUpdated: (Asignatura) -> Unit,
    onDialogDismissed: () -> Unit
) {
    var codigo by remember { mutableStateOf(asignatura.codigo) }
    var nombre by remember { mutableStateOf(asignatura.nombre) }
    var descripcion by remember { mutableStateOf(asignatura.descripcion) }
    var horas by remember { mutableIntStateOf(asignatura.horas!!) }

    AlertDialog(
        title = { Text(text = "Actualizar asignatura") },
        onDismissRequest = {},
        confirmButton = {
            Button(
                onClick = {
                    val newAsignatura = Asignatura(
                        id = asignatura.id,
                        userId = asignatura.userId,
                        codigo = codigo,
                        nombre = nombre,
                        descripcion = descripcion,
                        horas = horas
                    )
                    onAsignaturaUpdated(newAsignatura)
                    codigo = ""
                    nombre = ""
                    descripcion = ""
                    horas = 0
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
                    value = codigo ?: "",
                    onValueChange = { codigo = it },
                    label = { Text("Código") }
                )
                Spacer(modifier = Modifier.height(10.dp))
                TextField(
                    value = nombre ?: "",
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") }
                )
                Spacer(modifier = Modifier.height(10.dp))
                TextField(
                    value = descripcion ?: "",
                    onValueChange = { descripcion = it },
                    label = { Text("Descripcion") }
                )
                Spacer(modifier = Modifier.height(10.dp))
                TextField(
                    value = horas.toString(),
                    onValueChange = { horas = it.toIntOrNull() ?: 0 },
                    label = { Text("Total de horas") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType =
                        KeyboardType.Number
                    )
                )
            }
        }
    )
}