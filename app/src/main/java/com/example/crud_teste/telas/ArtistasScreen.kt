package com.example.crud_teste.telas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.crud_teste.components.ArtistaList
import com.example.crud_teste.components.GenericListView
import com.example.crud_teste.components.GlobalText
import com.example.crud_teste.components.GlobalTextColor
import com.example.crud_teste.components.SideBar
import com.example.crud_teste.data.model.Artista
import com.example.crud_teste.interfaces.ICrudService
import com.example.crud_teste.services.ArtistaCrudService
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistasScreen(navController: NavController, artistaCrudService: ArtistaCrudService) {
    val context = LocalContext.current
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    var artistas : List<Artista> = listOf<Artista>()

    // Chamada apropriada para obter artistas quando a tela é acessada
    LaunchedEffect(Unit) {
        try {
            coroutineScope.launch {
                artistas = artistaCrudService.getAll();
            }
            //viewModel.getArtistas()
        } catch (e: Exception) {
            // Trate o erro conforme necessário
            // Aqui você pode definir um estado de erro ou lidar com o erro de outra forma
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            SideBar(drawerState, context, navController)
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        GlobalTextColor(
                            text = "Artistas",
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.LightGray),
                            style = MaterialTheme.typography.titleLarge
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            coroutineScope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(Icons.Filled.Menu, contentDescription = "Open Navigation Menu")
                        }
                    },
                    actions = {
                        Spacer(Modifier.width(48.dp))
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = Color.LightGray
                    )
                )
            }
        ) { paddingValues ->
           val artistasState = artistaCrudService.artistaState.value
            // Verifica se a lista de artistas não está vazia antes de exibi-la

            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                items(artistasState.size) { index ->
                    val artista = artistasState[index]
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp, horizontal = 8.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        GlobalText(text = "Nome: ${artista.Nome}")
                        GlobalText(text = "Data: ${artista.Data}")
                        GlobalText(text = "Biografia: ${artista.Biografia}")
                    }
                }
            }

        }
    }
}