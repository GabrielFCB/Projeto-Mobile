package com.example.crud_teste

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.crud_teste.data.model.UserState
import com.example.crud_teste.data.network.SupabaseClient
import com.example.crud_teste.ui.theme.Crud_TesteTheme
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.compose.auth.composable.rememberLoginWithGoogle
import io.github.jan.supabase.compose.auth.composeAuth
import io.github.jan.supabase.compose.auth.ui.ProviderButtonContent
import io.github.jan.supabase.gotrue.providers.Google
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Crud_TesteTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "login") {
                    composable("login") { MainScreen(navController) }
                    composable("home") { HomeScreen(navController) }
                }
            }
        }
    }
}

@Composable
fun MainScreen(navController: NavController, viewModel: SupabaseAuthViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val context = LocalContext.current
    val userState by viewModel.userState

    var userEmail by remember { mutableStateOf("") }
    var userPassword by remember { mutableStateOf("") }

    val action = SupabaseClient.client.composeAuth.rememberLoginWithGoogle(
        onResult = { result -> viewModel.checkGoogleLoginStatus(context, result) },
        fallback = {}
    )

    LaunchedEffect(userState) {
        if (userState is UserState.Success) {
            Navigator.navigateToHome(navController)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        TextField(
            value = userEmail,
            onValueChange = { userEmail = it },
            placeholder = { Text("Enter email") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = userPassword,
            onValueChange = { userPassword = it },
            placeholder = { Text("Enter password") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { viewModel.signUp(context, userEmail, userPassword) }) {
            Text("Sign Up")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { viewModel.login(context, userEmail, userPassword) }) {
            Text("Login")
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedButton(onClick = { action.startFlow() }) {
            Text("Login via Google")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: SupabaseAuthViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val context = LocalContext.current
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()  // Obtém o CoroutineScope para o Composable

    // Dados placeholder para simular a lista
    val itemsList = List(5) { "Item ${it + 1}" }  // Isto pode ser substituído por uma chamada de API ou dados vindos de um banco de dados

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(drawerState, viewModel, context, navController)  // Passa viewModel, context e navController para o Drawer
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Centro Cultural",
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.LightGray),
                            textAlign = TextAlign.Center,
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
                        ListItem(item = itemsList[index])
                    }
                }
            }
        }
    }
}

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


//@OptIn(SupabaseExperimental::class)
//@Composable
//fun MainScreen(
//    viewModel: SupabaseAuthViewModel=androidx.lifecycle.viewmodel.compose.viewModel(),
//){
//    val context = LocalContext.current
//    val userState by viewModel.userState
//
//    var userEmail by remember { mutableStateOf("") }
//    var userPassword by remember{ mutableStateOf("") }
//    var currentUserState by remember { mutableStateOf("") }
//
//    val action= SupabaseClient.client.composeAuth.rememberLoginWithGoogle(
//        onResult={result->viewModel.checkGoogleLoginStatus(context,result)},
//        fallback = {}
//    )
//
//    LaunchedEffect(Unit){
//        viewModel.isUserLoggedIn(
//            context,
//        )
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(8.dp)
//    ){
//        TextField(
//            value=userEmail,
//            placeholder={
//                Text(text="Enter email")
//            },
//            onValueChange={
//                userEmail=it
//            }
//        )
//        Spacer(modifier = Modifier.padding(8.dp))
//        TextField(
//            value=userPassword,
//            placeholder={
//                Text(text="Enter password")
//            },
//            onValueChange={
//                userPassword=it
//            }
//        )
//        Spacer(modifier = Modifier.padding(8.dp))
//        //A importação do botão pode estar errada
//        Button(onClick={
//            viewModel.signUp(
//                context,
//                userEmail,
//                userPassword,
//            )
//        }){
//            Text(text="Sign Up")
//        }
//        Button(onClick={
//            viewModel.login(
//                context,
//                userEmail,
//                userPassword,
//            )
//        }){
//            Text(text="Login")
//        }
////        Button(onClick = {
////            action.startFlow()
////        }) {
////            Text(text="Login via Google")
////        }
//        OutlinedButton(
//            onClick = { action.startFlow() },
//            content={ProviderButtonContent(provider = Google)})
//
//        Button(
//            colors=ButtonDefaults.buttonColors(containerColor = Color.Red),
//            onClick={
//                viewModel.logout(context)
//            }){
//            Text(text="Logout")
//        }
//
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ){
//            Button(onClick = {viewModel.saveNote()}) {
//                Text(text = "Save")
//            }
//            Button(onClick = {viewModel.getNote()}) {
//                Text(text = "Fetch")
//            }
//            Button(onClick = {viewModel.updateNote()}) {
//                Text(text = "Update")
//            }
//            Button(onClick = {viewModel.deleteNote()}) {
//                Text(text = "Delete")
//            }
//
//        }
//
//        when(userState){
//            is UserState.Loading -> {
//                LoadingComponent()
//            }
//            is UserState.Success ->{
//                val message =(userState as UserState.Success).message
//                currentUserState=message
//            }
//            is UserState.Error -> {
//                val message = (userState as UserState.Error).message
//                currentUserState=message
//            }
//        }
//
//        if(currentUserState.isNotEmpty()){
//            Text(text=currentUserState)
//        }
//    }
//
//}
