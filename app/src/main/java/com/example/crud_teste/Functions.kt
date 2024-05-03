package com.example.crud_teste

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@Composable
fun ListItem(item: String) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        Box(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Placeholder Image", style = MaterialTheme.typography.headlineMedium)
        }
        Spacer(Modifier.height(8.dp))
        Text(text = item, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun DrawerContent(drawerState: DrawerState, viewModel: SupabaseAuthViewModel, context: Context, navController: NavController) {
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
        Button(onClick = { /* Adicionar funcionalidade de navegação */ }) { Text("Home") }
        Spacer(Modifier.height(8.dp))
        Button(onClick = { /* Adicionar funcionalidade de navegação */ }) { Text("Obras") }
        Spacer(Modifier.height(8.dp))
        Button(onClick = { /* Adicionar funcionalidade de navegação */ }) { Text("Artistas") }
        Spacer(Modifier.height(8.dp))
        Button(onClick = { /* Adicionar funcionalidade de navegação */ }) { Text("Exposição") }
        Spacer(Modifier.height(8.dp))
        Button(onClick = { /* Adicionar funcionalidade de navegação */ }) { Text("Administrador") }
        Spacer(Modifier.height(8.dp))
        Button(onClick = {
            coroutineScope.launch {
                viewModel.logout(context)
                navController.navigate("login")
                drawerState.close()
            }
        }) { Text("Logout") }
    }
}
