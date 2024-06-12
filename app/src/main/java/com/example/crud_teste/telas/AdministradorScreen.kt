package com.example.crud_teste.telas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.crud_teste.components.SideBar
import com.example.crud_teste.Navigator
import com.example.crud_teste.components.GlobalText
import com.example.crud_teste.components.GlobalTextColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdministradorScreen(navController: NavController) {
    val context = LocalContext.current
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()  // Obtém o CoroutineScope para o Composable

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
                            text = "Administrador",
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
                Button(
                    onClick = { Navigator.navigateToCadastrarArtista(navController) },
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth()
                        .height(48.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.primary),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    GlobalText("Cadastrar Artista")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { Navigator.navigateToAtualizarArtista(navController) },
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth()
                        .height(48.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.primary),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    GlobalText("Atualizar Artista")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { Navigator.navigateToCadastrarObra(navController) },
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth()
                        .height(48.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.primary),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    GlobalText("Cadastrar Obra")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { Navigator.navigateToAtualizarObra(navController) },
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth()
                        .height(48.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.primary),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    GlobalText("Atualizar Obra")
                }
            }
        }
    }
}

