package com.example.crud_teste.telas

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
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
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposicaoScreen(navController: NavController) {
    val context = LocalContext.current
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()  // Obtém o CoroutineScope para o Composable

    // Dados placeholder para simular a lista
    val itemsList = List(5) { "Item ${it + 1}" }  // Isto pode ser substituído por uma chamada de API ou dados vindos de um banco de dados

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            SideBar(drawerState, context, navController)  // Passa viewModel, context e navController para o Drawer
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        GlobalTextColor(
                            text = "Exposição",
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
            Column(modifier = Modifier.padding(paddingValues)) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    item {
                        // Primeiro item personalizado
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                            ) {
                                GlobalText(
                                    text = "Centelhas em Movimento",
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                // Aqui você pode adicionar a lógica para carregar a imagem com Glide
                                // Placeholder da imagem
                                GlideImage(
                                    url = "https://www.unifor.br/documents/20143/573160/Cabe%25C3%25A7a%2bde%2bmulato%2b800.jpg/a7b8f2ff-6f8b-2c92-9f7b-c04586192e02?t%3d1709747627248",
                                    modifier = Modifier.fillMaxWidth().padding(16.dp).clickable(onClick = { Navigator.navigateToExposicao(navController) }),
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                GlobalText(
                                    text = "“Por acreditarmos que obras de arte podem estar em brasa quando reavivadas pelos olhares dos públicos, concebemos a fricção entre elas como ação que desprende centelhas pelos ares”. É com essa percepção que Paulo Miyada e Tiago Gualberto montaram a exposição “Centelhas em Movimento”, com obras da Coleção Igor Queiroz Barroso. Após bem-sucedida temporada no Instituto Tomie Ohtake, em São Paulo, a mostra chega à Fortaleza, terra natal do colecionador. A abertura da exposição acontecerá no dia 12 de março, às 19h, no Espaço Cultural Unifor.",
                                )

                        }
                    }
                    items(itemsList.size) { index ->
                    }
                }
            }
        }
    }
}