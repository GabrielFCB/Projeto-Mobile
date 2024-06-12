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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
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
                    items(itemsList.size) { index ->
                    }
                }
            }
        }
    }
}