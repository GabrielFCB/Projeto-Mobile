package com.example.crud_teste.telas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.crud_teste.Navigator
import com.example.crud_teste.components.GlideImage
import com.example.crud_teste.components.GlobalText
import com.example.crud_teste.components.GlobalTextColor
import com.example.crud_teste.components.SideBar
import com.example.crud_teste.data.model.Obra
import com.example.crud_teste.services.ObraCrudService
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VisualizarObraScreen(navController: NavController, obraCrudService: ObraCrudService) {
    val context = LocalContext.current
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    var obras : List<Obra> = listOf<Obra>()

    // Chamada apropriada para obter artistas quando a tela é acessada
    LaunchedEffect(Unit) {
        try {
            coroutineScope.launch {
                obras = obraCrudService.getAll();
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
                            text = "Obras",
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
            val obrasState = obraCrudService.obraState.value
            // Verifica se a lista de artistas não está vazia antes de exibi-la

            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                items(obrasState.size) { index ->
                    val obra = obrasState[index]
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp, horizontal = 8.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        GlobalText(text = "Nome: ${obra.nome}")
                        GlobalText(text = "Autor: ${obra.autor}")
                        GlobalText(text = "Data: ${obra.data}")
                        GlobalText(text = "Descrição: ${obra.descricao}")
                        GlideImage(
                            url = obra.link,
                            modifier = Modifier.fillMaxWidth().padding(16.dp)
                        )
                        Button(
                            onClick = {
                                var obraId=obra.id
                                if (obraId != null) {
                                    obraCrudService.obraId=obraId
                                }
                                Navigator.navigateToAtualizarObra(navController)
                            },
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .fillMaxWidth()
                                .height(48.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.primary),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            GlobalText("Editar Obra")
                        }
                        Button(
                            onClick = { coroutineScope.launch {
                                var obraId=obra.id
                                if (obraId != null) {
                                    obraCrudService.delete(obraId)
                                    obras = obraCrudService.getAll();
                                }
                            } },
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .fillMaxWidth()
                                .height(48.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.primary),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            GlobalText("Excluir Obra")
                        }
                    }

                }
            }

        }
    }
}