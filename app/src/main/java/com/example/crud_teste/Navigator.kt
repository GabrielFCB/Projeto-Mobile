package com.example.crud_teste

import androidx.navigation.NavController

object Navigator {
    fun navigateToHome(navController: NavController) {
        navController.navigate("home") {
            popUpTo("login") { inclusive = true }
        }
    }

    fun navigateToLogin(navController: NavController) {
        navController.navigate("login") {
            popUpTo("home") { inclusive = true }
        }
    }
}