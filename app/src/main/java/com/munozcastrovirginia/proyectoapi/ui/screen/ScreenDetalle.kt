package com.munozcastrovirginia.proyectoapi.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenDetalle(
    idAsignatura: String,
    auth: AuthManager,
    firestore: FirestoreManager,
    navigateToLogin: () -> Unit,
) {
    val user = auth.getCurrentUser()
    val factory = InicioViewModelFactory(firestore)
    val inicioViewModel = viewModel(InicioViewModel::class.java, factory = factory)
    val asignatura by inicioViewModel.asignatura.collectAsState()
    val uiState by inicioViewModel.uiState.collectAsState()

    LaunchedEffect(idAsignatura) {
        inicioViewModel.getAsignaturaById(idAsignatura)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (user?.photoUrl != null) {
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
                                text = user?.email ?: "Sin correo",
                                fontSize = 12.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(
                        ContextCompat.getColor(
                            LocalContext.current,
                            R.color.gris_oscuro
                        )
                    )
                ),
                actions = {
                    IconButton(onClick = {
                        inicioViewModel.onLogoutSelected()
                    }) {
                        Icon(
                            Icons.AutoMirrored.Outlined.ExitToApp,
                            contentDescription = "Cerrar sesión"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { inicioViewModel.onAddAsignaturaSelected() },
                containerColor = Color.Gray
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Añadir asignatura")
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            if (uiState.showLogoutDialog) {
                LogoutDialog(
                    onDismiss = { inicioViewModel.dismisShowLogoutDialog() },
                    onConfirm = {
                        auth.signOut()
                        navigateToLogin()
                        inicioViewModel.dismisShowLogoutDialog()
                    }
                )
            }
        }
    }
}