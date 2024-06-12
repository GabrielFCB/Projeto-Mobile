package com.example.crud_teste.telas


import android.content.ContentValues
import android.util.Log
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.crud_teste.components.GlobalText
import com.example.crud_teste.components.GlobalTextColor
//import com.example.crud_teste.SupabaseAuthViewModel
import com.example.crud_teste.data.model.Artista
import com.example.crud_teste.data.model.Obra
import com.example.crud_teste.services.ObraCrudService
import io.ktor.websocket.Frame
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastrarObraScreen(navController: NavController, obraCrudService: ObraCrudService) {
    var Nome by remember { mutableStateOf("") }
    var Autor by remember { mutableStateOf("") }
    var Data by remember { mutableStateOf("") }
    var Descricao by remember { mutableStateOf("") }
    var Link  by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { GlobalTextColor("Cadastrar Obra",style = MaterialTheme.typography.titleLarge) },
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
                label = { GlobalText("Nome da Obra") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
            OutlinedTextField(
                value = Autor,
                onValueChange = { Autor = it },
                label = { GlobalText("Autor da Obra") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
            OutlinedTextField(
                value = Data,
                onValueChange = { Data = it },
                label = { GlobalText("Data de Criação") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
            OutlinedTextField(
                value = Descricao,
                onValueChange = { Descricao = it },
                label = { GlobalText("Sobre a Obra") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .height(120.dp)
            )
            OutlinedTextField(
                value = Link,
                onValueChange = { Link = it },
                label = { GlobalText("Link com imagem") },
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
                    val novaObra = Obra(
                        autor=Autor,
                        nome = Nome,
                        data= Data,
                        descricao = Descricao,
                        link=Link
                    )
                    try {
                        coroutineScope.launch {
                            obraCrudService.insert(novaObra)
                        }
                        //viewModel.getArtistas()
                    } catch (e: Exception) {
                        // Trate o erro conforme necessário
                        // Aqui você pode definir um estado de erro ou lidar com o erro de outra forma
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
            ) {
                GlobalText("Cadastrar")
            }
        }
    }
}
