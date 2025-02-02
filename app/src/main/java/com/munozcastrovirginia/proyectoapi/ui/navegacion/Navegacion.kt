package com.munozcastrovirginia.proyectoapi.ui.navegacion

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.munozcastrovirginia.proyectoapi.data.AuthManager
import com.munozcastrovirginia.proyectoapi.ui.screen.ForgotPasswordScreen
import com.munozcastrovirginia.proyectoapi.ui.screen.LoginScreen
import com.munozcastrovirginia.proyectoapi.ui.screen.ScreenLista
import com.munozcastrovirginia.proyectoapi.ui.screen.SignUpScreen

@Composable
fun Navegacion(auth: AuthManager) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = login
    ) {
        composable<login> {
            LoginScreen(
                auth,
                { navController.navigate(signUp) },
                {
                    navController.navigate(listaPersonajes) {
                        popUpTo(login) { inclusive = true }
                    }
                },
                { navController.navigate(forgotPassword) }
            )
        }
        composable<signUp> {
            SignUpScreen(
                auth
            ) { navController.popBackStack() }
        }

        composable <forgotPassword> {
            ForgotPasswordScreen(
                auth
            ) { navController.navigate(login) {
                popUpTo(login){ inclusive = true }
            } }
        }

        composable<listaPersonajes> {
            ScreenLista(
                auth,
                viewModel(),
                {
                    navController.navigate(login) {
                        popUpTo(listaPersonajes){ inclusive = true }
                    }
                }
            )
        }
    }
}
