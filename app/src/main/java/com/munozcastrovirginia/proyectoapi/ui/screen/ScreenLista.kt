package com.munozcastrovirginia.proyectoapi.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.munozcastrovirginia.proyectoapi.R
import com.munozcastrovirginia.proyectoapi.data.AuthManager
import com.munozcastrovirginia.proyectoapi.data.FirestoreManager
import com.munozcastrovirginia.proyectoapi.model.Asignatura
import com.munozcastrovirginia.proyectoapi.model.AsignaturaDB


// Composable que muestra la lista de personajes
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenLista(auth: AuthManager, firestore: FirestoreManager, navigateToLogin:() -> Unit) {
//    val characters by viewModel2.characterList.collectAsState()
//    val isLoading = characters.isEmpty()

    var showDialog by remember { mutableStateOf(false) }
    val user = auth.getCurrentUser()
    val factory = InicioViewModelFactory(firestore)
    val inicioViewModel = viewModel(InicioViewModel::class.java, factory = factory)
    val uiState by inicioViewModel.uiState.collectAsState()

    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if(user?.photoUrl != null){
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(user?.photoUrl)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "Imagen",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .size(40.dp)
                            )
                        } else {
                            Image(
                                painter = painterResource(R.drawable.ic_usuario),
                                contentDescription = "Foto de perfil por defecto",
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .size(40.dp)
                                    .clip(CircleShape)
                            )

                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = user?.displayName ?: "Anónimo",
                                fontSize = 20.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text =  user?.email ?: "Sin correo",
                                fontSize = 12.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(ContextCompat.getColor(LocalContext.current, R.color.gris_oscuro))),
                actions = {
                    IconButton(onClick = {
                        showDialog = true
                    }) {
                        Icon(Icons.AutoMirrored.Outlined.ExitToApp, contentDescription = "Cerrar sesión")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {inicioViewModel.onAddAsignaturaSelected()}
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Añadir asignatura")
            }
        }
    ){
        Box(
            modifier = Modifier.fillMaxSize().padding(it)
        ){
            if (uiState.showLogoutDialog){
                LogoutDialog(
                    onDismiss = { showDialog = false },
                    onConfirm = {
                        auth.signOut()
                        navigateToLogin()
                        showDialog = false
                    }
                )
            }

            if (!uiState.asignaturas.isNullOrEmpty()) {
                LazyColumn(
                    modifier = Modifier.padding(16.dp)
                ) {
                    items(uiState.asignaturas) { asignatura ->
                        AsignaturaItem(
                            asignatura = asignatura, {}
//                            inicioViewModel.deleteAsignaturaById(asignatura.id ?: "")
                        ) {
//                            inicioViewModel.updateAsignatura(it)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }
    }


}

@Composable
fun AsignaturaItem(asignatura: Asignatura, deleteAsignatura: () -> Unit, updateAsignatura: (Asignatura) -> Unit) {

    var showDeleteAsignaturaDialog by remember { mutableStateOf(false) }
    var showUpdateAsignaturaDialog by remember { mutableStateOf(false) }

    if (showDeleteAsignaturaDialog) {
//        DeleteAsignaturaDialog(
//            onConfirmDelete = {
//                deleteAsignatura()
//                showDeleteAsignaturaDialog = false
//            },
//            onDismiss = { showDeleteAsignaturaDialog = false }
//        )
    }

    if (showUpdateAsignaturaDialog) {
//        UpdateAsignaturaDialog(
//            asignatura = asignatura,
//            onAsignaturaUpdate = { asignatura ->
//                onUpdateAsignatura(asignatura)
//                showUpdateAsignaturaDialog = false
//            },
//            onDialogDimissed = { showUpdateAsignaturaDialog = false}
//        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)

    ) {
        Row(modifier = Modifier.padding(16.dp)) {
//            AsyncImage(
//                model = character.image,
//                contentDescription = character.name,
//                modifier = Modifier.size(64.dp)
//            )
//            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = asignatura.codigo?: "", style = MaterialTheme.typography.titleLarge)
                Text(text = "Nombre: ${asignatura.nombre}", style = MaterialTheme.typography.bodySmall)
                Text(text = "Descripcion: ${asignatura.descripcion}", style = MaterialTheme.typography.bodySmall)
                Text(text = "Horas: ${asignatura.horas}", style = MaterialTheme.typography.bodySmall)
            }
        }
        Row(modifier = Modifier.padding(16.dp)) {
            IconButton(
                onClick = {showDeleteAsignaturaDialog = true}
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Borrar Asignatura"
                )
            }
            IconButton(
                onClick = {showUpdateAsignaturaDialog = true}
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Actualizar Asignatura"
                )
            }
        }
    }
}

@Composable
fun LogoutDialog(onDismiss: () -> Unit, onConfirm: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Cerrar Sesión") },
        text = {
            Text("¿Estás seguro de que deseas cerrar sesión?")
        },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text("Aceptar")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}