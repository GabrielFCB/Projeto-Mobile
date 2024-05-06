package com.example.crud_teste.telas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.crud_teste.SupabaseAuthViewModel
import com.example.crud_teste.data.model.Artista
import io.ktor.websocket.Frame

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastrarArtistaScreen(navController: NavController, viewModel: SupabaseAuthViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    var Nome by remember { mutableStateOf("") }
    var Data by remember { mutableStateOf("") }
    var Biografia by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cadastrar Artista") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Go Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            OutlinedTextField(
                value = Nome,
                onValueChange = { Nome = it },
                label = { Text("Nome do Artista") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
            OutlinedTextField(
                value = Data,
                onValueChange = { Data = it },
                label = { Text("Data de Nascimento") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
            OutlinedTextField(
                value = Biografia,
                onValueChange = { Biografia = it },
                label = { Text("Biografia") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .height(120.dp)
            )
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Button(
                onClick = {
                    val novoArtista = Artista(
                        Nome = Nome,
                        Data= Data,
                        Biografia = Biografia
                    )
                    viewModel.saveArtista(novoArtista)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
            ) {
                Text("Cadastrar")
            }
        }
    }
}
