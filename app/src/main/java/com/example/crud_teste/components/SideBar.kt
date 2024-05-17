package com.example.crud_teste.components

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.crud_teste.Navigator
import kotlinx.coroutines.launch

//Refere-se aos itens da sidebar
@Composable
fun SideBar(drawerState: DrawerState, context: Context, navController: NavController) {
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        IconButton(
            onClick = {
                coroutineScope.launch {
                    drawerState.close()
                }
            }
        ) {
            Icon(Icons.Filled.ArrowBack, contentDescription = "Close Drawer")
        }
        Spacer(Modifier.height(16.dp))
        Button(onClick = {
            coroutineScope.launch {
                drawerState.close()
                Navigator.navigateToHome(navController)
            }
        }) { Text("Home") }
        Spacer(Modifier.height(8.dp))
        Button(onClick = {
            coroutineScope.launch {
                drawerState.close()
                Navigator.navigateToObras(navController)
            }
        }) { Text("Obras") }
        Spacer(Modifier.height(8.dp))
        Button(onClick = {
            coroutineScope.launch {
                drawerState.close()
                Navigator.navigateToArtistas(navController)
            }
        }) { Text("Artistas") }
        Spacer(Modifier.height(8.dp))
        Button(onClick = {
            coroutineScope.launch {
                drawerState.close()
                Navigator.navigateToExposicao(navController)
            }
        }) { Text("Exposição") }
        Spacer(Modifier.height(8.dp))
        Button(onClick = {
            coroutineScope.launch {
                drawerState.close()
                Navigator.navigateToAdministrador(navController)
            }
        }) { Text("Administrador") }
        Button(onClick = {
            coroutineScope.launch {
                drawerState.close()
                Navigator.navigateToAcessibilidade(navController)
            }
        }) { Text("Acessibilidade") }
        Spacer(Modifier.height(8.dp))
        Button(onClick = {
            coroutineScope.launch {
                //viewModel.logout(context)
                navController.navigate("login")
                drawerState.close()
            }
        }) { Text("Logout") }
    }
}
