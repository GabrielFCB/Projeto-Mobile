package com.example.crud_teste

import androidx.navigation.NavController

object Navigator {
    fun navigateToHome(navController: NavController) {
        navController.navigate("home")
    }

    fun navigateToObras(navController: NavController) {
        navController.navigate("obras")
    }

    fun navigateToArtistas(navController: NavController) {
        navController.navigate("artistas")
    }

    fun navigateToExposicao(navController: NavController) {
        navController.navigate("exposicao")
    }

    fun navigateToAdministrador(navController: NavController) {
        navController.navigate("administrador")
    }

    fun navigateToLogin(navController: NavController) {
        navController.navigate("login") {
            popUpTo("home") { inclusive = true }
        }
    }
}